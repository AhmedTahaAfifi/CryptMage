package com.example.cryptmage.ui.screens.cloudSync

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.repository.GoogleDriveManager
import com.example.cryptmage.data.repository.SessionManager
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.data.repository.BackupManager
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.utils.HelperMethods
import kotlinx.coroutines.launch
import com.google.android.gms.auth.api.identity.Identity

class CloudSyncViewModel(
    private val googleDriveManager: GoogleDriveManager,
    private val sessionManager: SessionManager,
    private val vaultRepository: VaultRepository,
    private val backupManager: BackupManager
) : BaseViewModel<CloudSyncUIState, CloudSyncEffect>(CloudSyncUIState()), CloudSyncInteraction {

    init {
        val savedEmail = sessionManager.getUserEmail()
        updateState {
            it.copy(
                isDriveConnected = savedEmail != null,
                userEmail = savedEmail
            )
        }
        loadCloudSyncData()
    }

    private fun loadCloudSyncData() {
        val email = viewState.value.userEmail
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                val entryCount = vaultRepository.getEntryCount()
                val vaultSize = googleDriveManager.getDatabaseSize()
                val backupCount = googleDriveManager.getBackupCount(email)
                val lastSyncTimestamp = googleDriveManager.getLastSyncTime(email)
                
                Triple(entryCount, vaultSize, Pair(backupCount, lastSyncTimestamp))
            },
            onSuccess = { (entryCount, vaultSize, backupInfo) ->
                val (backupCount, lastSyncTimestamp) = backupInfo
                updateState {
                    it.copy(
                        entryCount = entryCount,
                        vaultSize = vaultSize,
                        backupCount = backupCount,
                        lastSyncTime = HelperMethods.formatDate(lastSyncTimestamp)
                    )
                }
            }
        )
    }

    override fun onLinkDriveClick(activityContext: Context) {
        if (!viewState.value.isDriveConnected) {
            this.linkDrive(activityContext)
        }
    }

    override fun onSyncClick() {
        val email = viewState.value.userEmail
        if (email.isNullOrBlank()) {
            showSnackBar(
                messageId = R.string.error_drive_not_linked,
                status = SnackBarState.States.Error
            )
            return
        }

        this.onSyncNow(email)
    }

    override fun onDrivePermissionGranted(email: String?) {
        if (email == null) return
        
        sessionManager.saveUserEmail(email)
        updateState { 
            it.copy(
                isDriveConnected = true,
                userEmail = email
            ) 
        }
        loadCloudSyncData()
        showSnackBar(
            messageId = R.string.drive_linked_success,
            status = SnackBarState.States.Success
        )
    }

    override fun onExportClick(activityContext: Context) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                val exportFile = backupManager.prepareBackupFile()
                
                // 3. Share URI
                val uri = androidx.core.content.FileProvider.getUriForFile(
                    activityContext,
                    "${activityContext.packageName}.provider",
                    exportFile
                )
                
                val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "application/octet-stream"
                    putExtra(android.content.Intent.EXTRA_STREAM, uri)
                    addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                activityContext.startActivity(android.content.Intent.createChooser(intent, "Share Backup"))
            },
            onError = { errorState ->
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

    private fun linkDrive(activityContext: Context) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                val email = this.googleDriveManager.signIn(activityContext)
                updateState { it.copy(userEmail = email) }

                val client = Identity.getAuthorizationClient(activityContext)
                val authRequest = googleDriveManager.getDriveAuthorizationRequest()

                client.authorize(authRequest).addOnSuccessListener { result ->
                    if (result.hasResolution()) {
                        viewModelScope.launch {
                            sendEffect(CloudSyncEffect.RequestDrivePermission(result.pendingIntent!!.intentSender))
                        }
                    } else {
                        onDrivePermissionGranted(email)
                    }
                }
            },
            onError = { errorState ->
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

    private fun onSyncNow(email: String) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                sessionManager.database?.let { db ->
                    db.query("PRAGMA wal_checkpoint(FULL)", null).use { cursor ->
                        cursor.moveToFirst()
                    }
                }
                this.googleDriveManager.uploadDatabaseFile(email)
            },
            onSuccess = {
                loadCloudSyncData()
                showSnackBar(
                    messageId = R.string.sync_success,
                    status = SnackBarState.States.Success
                )
            },
            onError = { errorState ->
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }
}
