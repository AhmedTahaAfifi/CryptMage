package com.example.cryptmage.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.models.VaultEntry
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.domain.usecases.DeleteVaultEntryUseCase
import com.example.cryptmage.domain.usecases.GetVaultEntryUseCase
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.ui.screens.details.toUi
import com.example.cryptmage.utils.ClipboardManager

class DetailsViewModel(
    private val getVaultEntryUseCase: GetVaultEntryUseCase,
    private val deleteVaultEntryUseCase: DeleteVaultEntryUseCase,
    private val clipboardManager: ClipboardManager
) : BaseViewModel<DetailsUIState, DetailsEffect>(DetailsUIState()), DetailsInteraction {

    override fun onPasswordClick() {
        updateState { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    override fun onPasswordCopiedClick(password: String) {
        this.clipboardManager.copy(password)
        updateState { it.copy(isPasswordCopied = true) }
    }

    override fun onEmailCopiedClick(email: String) {
        this.clipboardManager.copy(email)
        updateState { it.copy(isEmailCopied = true) }
    }

    override fun onDelete() {
        val vaultIdToDelete = viewState.value.vaultId
        if (vaultIdToDelete != 0) { // vaultId = 0 is default and means no entry loaded
            AppRequests.makeRequest(
                scope = viewModelScope,
                onStarted = { updateState { it.copy(isLoading = true) } },
                onCompleted = { updateState { it.copy(isLoading = false) } },
                request = { deleteVaultEntryUseCase(vaultIdToDelete) },
                onSuccess = {
                    sendEffect(DetailsEffect.VaultDeleted)
                    showSnackBar(
                        messageId = R.string.vault_entry_deleted_success,
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

    fun loadVaultEntry(vaultId: Int) {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = { getVaultEntryUseCase(vaultId) },
            onSuccess = { entry ->
                entry?.let { vaultEntry ->
                    updateState { vaultEntry.toUi(isLoading = false) }
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

}