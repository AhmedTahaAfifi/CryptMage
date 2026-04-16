package com.example.cryptmage.ui.screens.generatePassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.domain.usecases.GetVaultEntryUseCase
import com.example.cryptmage.domain.usecases.InsertVaultEntryUseCase
import com.example.cryptmage.domain.usecases.UpdateVaultEntryUseCase
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.utils.HelperMethods
import com.example.cryptmage.utils.PasswordGenerator
import com.example.cryptmage.utils.extensions.string.isValidEmail
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GeneratePasswordViewModel(
    private val insertVaultEntryUseCase: InsertVaultEntryUseCase,
    private val getVaultEntryUseCase: GetVaultEntryUseCase,
    private val updateVaultEntryUseCase: UpdateVaultEntryUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<GeneratePasswordUIState, GeneratePasswordEffect>(GeneratePasswordUIState()),
    GeneratePasswordInteractionListener {

    private val vaultId = savedStateHandle.toRoute<AppRoute.GeneratePassword>().vaultId

    init {
        if (vaultId != null) {
            this.observeExistingData()
        } else {
            this.generatePassword()
        }
    }

    override fun onLengthChange(length: Int) {
        updateState { it.copy(length = length) }
        generatePassword()
    }

    override fun onToggleUpperCase() {
        updateState { it.copy(upperCase = !it.upperCase) }
        generatePassword()
    }

    override fun onToggleNumbers() {
        updateState { it.copy(numbers = !it.numbers) }
        generatePassword()
    }

    override fun onToggleSymbols() {
        updateState { it.copy(symbols = !it.symbols) }
        generatePassword()
    }

    override fun onToggleAvoidAmbiguous() {
        updateState { it.copy(avoidAmbiguous = !it.avoidAmbiguous) }
        generatePassword()
    }

    override fun onVaultNameChange(name: String) {
        updateState { it.copy(vaultName = name, vaultNameError = null) }
    }

    override fun onEmailChange(email: String) {
        updateState { it.copy(email = email, emailError = null) }
    }

    override fun onRefresh() {
        generatePassword()
    }

    override fun onCopy() {
        showSnackBar(
            messageId = R.string.password_copied,
            status = SnackBarState.States.Success
        )
    }

    override fun onSave() {
        HelperMethods.createLog("savePassword")
        if (!checkRequiredFields()) return

        saveDataToVault()
    }

    private fun observeExistingData() {
        AppRequests.makeRequest(
            scope = viewModelScope,
            request = { getVaultEntryUseCase.invoke(vaultId!!) },
            onSuccess = {
                it.onEach { entry ->
                    entry?.let {
                        updateState { entry.toUi() }
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

    private fun checkRequiredFields(): Boolean {
        val state = viewState.value
        var isValid = true

        if (state.vaultName.isBlank()) {
            updateState { it.copy(vaultNameError = R.string.please_enter_a_vault_name) }
            isValid = false
        }
        if (state.email.isBlank() || !state.email.isValidEmail()) {
            updateState { it.copy(emailError = R.string.please_enter_a_valid_email) }
            isValid = false
        }

        return isValid
    }

    private fun saveDataToVault() {
        val state = viewState.value

        AppRequests.makeRequest(
            scope = viewModelScope,
            request = {
                if (this.vaultId != null) {
                    this.updateVaultEntryUseCase.invoke(state.toData())
                } else {
                    insertVaultEntryUseCase.invoke(state.toData())
                }
            },
            onCompleted = {
                showSnackBar(
                    messageId = R.string.password_saved,
                    status = SnackBarState.States.Success
                )
                sendEffect(GeneratePasswordEffect.NavigateUp)
            }
        )
    }

    private fun generatePassword() {
        val state = viewState.value
        val password = PasswordGenerator.generate(
            length = state.length,
            includeUpperCase = state.upperCase,
            includeNumbers = state.numbers,
            includeSymbols = state.symbols,
            avoidAmbiguous = state.avoidAmbiguous
        )
        updateState { it.copy(password = password) }
        updateState { it.copy(passwordStrengthSlug = PasswordStrength.analyze(password)) }
    }
}
