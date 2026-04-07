package com.example.cryptmage.ui.screens.generatePassword

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.R
import com.example.cryptmage.data.appViewStates.generatePassword.GeneratePasswordViewState
import com.example.cryptmage.data.repository.VaultRepository
import com.example.cryptmage.uitls.Constants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GeneratePasswordScreenVM(private val vaultRepository: VaultRepository): ViewModel() {

    private val _viewState = MutableStateFlow<GeneratePasswordViewState>(GeneratePasswordViewState())
    val viewState: StateFlow<GeneratePasswordViewState> get() = _viewState

    private val _viewEvents = Channel<Int>(Channel.BUFFERED)
    val viewEvent = _viewEvents.receiveAsFlow()

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
        viewModelScope.launch {
            _viewEvents.send(R.string.password_copied)
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
}