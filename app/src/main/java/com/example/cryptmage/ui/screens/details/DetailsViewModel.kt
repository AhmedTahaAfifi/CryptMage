package com.example.cryptmage.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cryptmage.R
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.domain.usecases.DeleteVaultEntryUseCase
import com.example.cryptmage.domain.usecases.GetVaultEntryUseCase
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.utils.ClipboardManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsViewModel(
    private val getVaultEntryUseCase: GetVaultEntryUseCase,
    private val deleteVaultEntryUseCase: DeleteVaultEntryUseCase,
    private val clipboardManager: ClipboardManager,
    saveStateHandle: SavedStateHandle
) : BaseViewModel<DetailsUIState, DetailsEffect>(DetailsUIState()), DetailsInteraction {

    private val vaultId = saveStateHandle.toRoute<AppRoute.Details>().vaultId

    init {
        this.observeVaultEntry()
    }

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

    fun observeVaultEntry() {
        AppRequests.makeRequest(
            scope = viewModelScope,
            onStarted = { updateState { it.copy(isLoading = true) } },
            onCompleted = { updateState { it.copy(isLoading = false) } },
            request = { this.getVaultEntryUseCase(this.vaultId) },
            onSuccess = {
                it.onEach { entry ->
                    entry?.let { vaultEntry ->
                        updateState { vaultEntry.toUi(isLoading = false) }
                    }
                }.launchIn(viewModelScope)
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