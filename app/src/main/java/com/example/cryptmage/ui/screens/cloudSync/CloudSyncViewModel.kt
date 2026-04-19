package com.example.cryptmage.ui.screens.cloudSync

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.repository.GoogleDriveManager
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.google.api.services.drive.Drive
import kotlinx.coroutines.launch
import com.google.android.gms.auth.api.identity.Identity

class CloudSyncViewModel(
    private val googleDriveManager: GoogleDriveManager
) : BaseViewModel<CloudSyncUIState, CloudSyncEffect>(CloudSyncUIState()), CloudSyncInteraction {

    private var driveService: Drive? = null

    init {
        updateState {
            it.copy(
                vaultSize = "0 KB",
                lastSyncTime = "Never"
            )
        }
    }

    override fun onLinkDriveClick(activityContext: Context) {
        if (!viewState.value.isDriveConnected) {
            this.linkDrive(activityContext)
        }
    }

    override fun onSyncClick() {
        val service = this.driveService ?: run {
            showSnackBar(
                messageId = R.string.error_drive_not_linked,
                status = SnackBarState.States.Error
            )
            return
        }

        this.onSyncNow(service)
    }

    override fun onDrivePermissionGranted(email: String) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                driveService = googleDriveManager.getDriveService(email)
            },
            onSuccess = {
                updateState { it.copy(isDriveConnected = true) }
                showSnackBar(
                    messageId = R.string.drive_linked_success,
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

    /*fun onExport() {
        // TODO: Implement export encrypted backup logic
    }*/

    private fun linkDrive(activityContext: Context) {
        android.util.Log.d("CloudSync", "Starting linkDrive...")
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                android.util.Log.d("CloudSync", "Calling googleDriveManager.signIn...")
                val email = this.googleDriveManager.signIn(activityContext)
                android.util.Log.d("CloudSync", "Sign in successful for: $email")

                // 1. Save the email for the next step
                updateState { it.copy(userEmail = email) }

                // 2. Request Drive Authorization
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
            onSuccess = {
                android.util.Log.d("CloudSync", "Step 1 (SignIn) complete")
            },
            onError = { errorState ->
                android.util.Log.e("CloudSync", "Link failure: ${errorState.exception?.message}")
                errorState.exception?.printStackTrace()
                showSnackBar(
                    messageId = errorState.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

    private fun onSyncNow(service: Drive) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = {
                this.googleDriveManager.uploadDatabaseFile(service)
            },
            onSuccess = {
                updateState { it.copy(lastSyncTime = "Just now") }
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
