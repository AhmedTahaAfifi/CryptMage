package com.example.cryptmage.ui.screens.generatePassword

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cryptmage.R
import com.example.cryptmage.data.appViewStates.generatePassword.GeneratePasswordViewState
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.moudels.VaultData
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.uitls.Constants
import com.example.cryptmage.uitls.HelperMethods
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GeneratePasswordScreenVM(private val vaultRepository: VaultRepository): ViewModel() {

    private val _viewState = MutableStateFlow(GeneratePasswordViewState())
    val viewState: StateFlow<GeneratePasswordViewState> get() = _viewState

    private val _viewEvents = MutableStateFlow(0)
    val viewEvent: StateFlow<Int> get() = _viewEvents

    init {
        this.generatePassword()
    }

    fun refreshPassword() {
        this.generatePassword()
    }

    fun updateLength(length: Int) {
        this._viewState.update { it.copy(data = it.data.copy(length = length)) }
        this.generatePassword()
    }

    fun updateVaultEntryData(vaultEntryData: VaultData) {
        this._viewState.update { it.copy(data = it.data.copy(vaultEntryData = vaultEntryData)) }
    }

    fun toggleUpperCase() {
        this._viewState.update { it.copy(data = it.data.copy(upperCase = !it.data.upperCase)) }
        this.generatePassword()
    }
    fun toggleNumbers() {
        this._viewState.update { it.copy(data = it.data.copy(numbers = !it.data.numbers)) }
        this.generatePassword()
    }
    fun toggleSymbols() {
        this._viewState.update { it.copy(data = it.data.copy(symbols = !it.data.symbols)) }
        this.generatePassword()
    }
    fun toggleAvoidAmbiguous() {
        this._viewState.update { it.copy(data = it.data.copy(avoidAmbiguous = !it.data.avoidAmbiguous))  }
        this.generatePassword()
    }

    fun copyPassword(context: Context, password: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("CryptMage Password", password))
        this.changeViewEvent(R.string.password_copied)
    }

    fun savePassword(navController: NavController) {
        HelperMethods.createLog("savePassword")
        if (!this.checkRequiredFields()) return

        this.saveDataToVault(navController)
    }

    private fun checkRequiredFields(): Boolean {
        val vaultData = this._viewState.value.data.vaultEntryData

        if (vaultData?.name.isNullOrBlank()) {
            HelperMethods.createLog("vaultData?.name")
            this.changeViewEvent(R.string.please_enter_a_vault_name)
            return false
        }
        if (vaultData.email.isNullOrBlank()) {
            HelperMethods.createLog("vaultData?.name")
            this.changeViewEvent(R.string.please_enter_a_valid_email)
            return false
        }
        /*if (vaultData.username.isNullOrBlank()) {
            this.changeViewEvent(R.string.please_enter_a_valid_email)
            return false
        }*/

        return true
    }

    private fun saveDataToVault(navController: NavController) {
        val data = this._viewState.value.data
        val strength = PasswordStrength.analyze(data.password)

        viewModelScope.launch {
            vaultRepository.insert(VaultData(
                name = data.vaultEntryData?.name,
                email = data.vaultEntryData?.email,
                password = data.password,
                passwordStrengthId = strength.labelId,
                passwordStrengthSlug = strength
            ))
            changeViewEvent(R.string.password_saved)
            navController.navigateUp()
        }
    }

    private fun generatePassword() {
        val data = this._viewState.value.data
        var charPool = Constants.GeneratePassword.LOWER_CASE

        Constants.GeneratePassword.let {
            if (data.upperCase) charPool += it.UPPER_CASE
            if (data.numbers) charPool += it.DIGIT
            if (data.symbols) charPool += it.SYMBOLS_CHARS
            if (data.avoidAmbiguous) charPool += charPool.filter { char -> char !in it.AMBIGUOUS }
        }

        if (charPool.isEmpty()) {
            this._viewState.update { it.copy(data = data.copy(password = ""), errorMessageId = R.string.no_character_type_selected) }
            return
        }

        val password = (1..data.length)
            .map { charPool[Random.nextInt(charPool.length)] }
            .joinToString("")
        this._viewState.update { it.copy(data = data.copy(password = password)) }
    }

    private fun changeViewEvent(newViewEvent: Int) {
        viewModelScope.launch {
            _viewEvents.emit(newViewEvent)
        }
    }
}