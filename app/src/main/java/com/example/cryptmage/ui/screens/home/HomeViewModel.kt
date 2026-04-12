package com.example.cryptmage.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.ui.screens.home.VaultDataUiState.Companion.toUi
import kotlinx.coroutines.flow.map

class HomeViewModel(private val vaultRepository: VaultRepository):
    BaseViewModel<HomeUIState, HomeEffect>(HomeUIState()) {

    init {
        this.getVaultEntries()
    }

    fun getVaultEntries() {
        AppRequests.makeRequest(
            scope = viewModelScope,
            request = this.vaultRepository::getAllVaultEntries,
            onSuccess = { vaultData ->
                updateState { state ->
                    state.copy(
                        vaultData = vaultData.map { flow -> flow.map { it.toUi() } }
                    )
                }
            },
            onError = {
                showSnackBar(
                    messageId = it.messageId,
                    status = SnackBarState.States.Error
                )
            }
        )
    }

}