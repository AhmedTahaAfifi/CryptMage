package com.example.cryptmage.ui.screens.generatePassword

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.moudels.VaultData
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.uitls.Constants
import com.example.cryptmage.uitls.HelperMethods
import kotlinx.coroutines.launch
import kotlin.random.Random

import com.example.cryptmage.ui.component.snackbar.SnackBarState
import com.example.cryptmage.ui.parent.BaseViewModel
import com.example.cryptmage.uitls.extensions.string.isValidEmail

class GeneratePasswordViewModel(private val vaultRepository: VaultRepository) :
    BaseViewModel<GeneratePasswordUIState, GeneratePasswordEffect>(GeneratePasswordUIState()),
    GeneratePasswordInteractionListener {

    init {
        this.generatePassword()
    }

    override fun onLengthChang(length: Int) {
        this.updateState { it.copy(length = length) }
        this.generatePassword()
    }

    override fun onVaultNameChange(name: String) {
        this.updateState { it.copy(vaultName = name) }
    }

    override fun onEmailChange(email: String) {
        this.updateState { it.copy(email = email) }
    }

    override fun onToggleUpperCase() {
        updateState { it.copy(upperCase = !it.upperCase) }
        this.generatePassword()
    }

    override fun onToggleNumbers() {
        updateState { it.copy(numbers = !it.numbers) }
        this.generatePassword()
    }

    override fun onToggleSymbols() {
        updateState { it.copy(symbols = !it.symbols) }
        this.generatePassword()
    }

    override fun onToggleAvoidAmbiguous() {
        updateState { it.copy(avoidAmbiguous = !it.avoidAmbiguous) }
        this.generatePassword()
    }

    override fun onRefresh() {
        this.generatePassword()
    }

    override fun onCopy() {
        showSnackBar(
            messageId = R.string.password_copied,
            status = SnackBarState.States.Success
        )
    }

    override fun onSave() {
        HelperMethods.createLog("savePassword")
        if (!this.checkRequiredFields()) return

        this.saveDataToVault()
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
        val strength = PasswordStrength.analyze(state.password)

        viewModelScope.launch {
            vaultRepository.insert(
                VaultData(
                    name = state.vaultName,
                    email = state.email,
                    password = state.password,
                    passwordStrengthId = strength.labelId,
                    passwordStrengthSlug = strength
                )
            )
            showSnackBar(
                messageId = R.string.password_saved,
                status = SnackBarState.States.Success
            )
            sendEffect(GeneratePasswordEffect.NavigateUp)
        }
    }

    private fun generatePassword() {
        val state = viewState.value
        var charPool = Constants.GeneratePassword.LOWER_CASE

        Constants.GeneratePassword.let {
            if (state.upperCase) charPool += it.UPPER_CASE
            if (state.numbers) charPool += it.DIGIT
            if (state.symbols) charPool += it.SYMBOLS_CHARS
            if (state.avoidAmbiguous) charPool += charPool.filter { char -> char !in it.AMBIGUOUS }
        }

        if (charPool.isEmpty()) {
            updateState {
                it.copy(
                    password = ""
                )
            }
            return
        }

        val password = (1..state.length)
            .map { charPool[Random.nextInt(charPool.length)] }
            .joinToString("")
        updateState { it.copy(password = password) }
    }
}
