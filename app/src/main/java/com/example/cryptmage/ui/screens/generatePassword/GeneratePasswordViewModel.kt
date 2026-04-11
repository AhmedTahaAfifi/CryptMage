package com.example.cryptmage.ui.screens.generatePassword

import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.domain.requests.AppRequests
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.ui.screens.generatePassword.GeneratePasswordUIState.Companion.toData
import com.example.cryptmage.utils.HelperMethods
import com.example.cryptmage.utils.PasswordGenerator
import com.example.cryptmage.utils.extensions.string.isValidEmail

class GeneratePasswordViewModel(private val vaultRepository: VaultRepository) :
    BaseViewModel<GeneratePasswordUIState, GeneratePasswordEffect>(GeneratePasswordUIState()),
    GeneratePasswordInteractionListener {

    init {
        generatePassword()
    }

    override fun onLengthChange(length: Int) {
        updateState { it.copy(length = length) }
        generatePassword()
    }

    override fun onVaultNameChange(name: String) {
        updateState { it.copy(vaultName = name) }
    }

    override fun onEmailChange(email: String) {
        updateState { it.copy(email = email) }
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

    private fun checkRequiredFields(): Boolean {
        val state = viewState.value

        if (state.vaultName.isBlank()) {
            showSnackBar(
                messageId = R.string.please_enter_a_vault_name,
                status = SnackBarState.States.Error
            )
            return false
        }
        if (state.email.isBlank() || !state.email.isValidEmail()) {
            showSnackBar(
                messageId = R.string.please_enter_a_valid_email,
                status = SnackBarState.States.Error
            )
            return false
        }

        return true
    }

    private fun saveDataToVault() {
        val state = viewState.value

        AppRequests.makeRequest(
            scope = viewModelScope,
            request = {
                vaultRepository.insert(state.toData())
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
