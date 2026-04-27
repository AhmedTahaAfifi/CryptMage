package com.example.cryptmage.ui.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.component.snackbar.SnackBarState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel<UI_STATE, UI_EFFECT>(
    initialState: UI_STATE
) : ViewModel(), KoinComponent {

    private val snackBarController: SnackBarController by inject()

    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = MutableSharedFlow<UI_EFFECT>()
    val viewEffect = _viewEffect.asSharedFlow()

    val snackBarState = snackBarController.snackBarState

    private val _appState = MutableStateFlow<AppStateFlow>(AppStateFlow.TopBarTitle(""))
    val appState = _appState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        this.hideSnackBar()
    }

    protected fun updateState(update: (UI_STATE) -> UI_STATE) {
        _viewState.update(update)
    }

    protected fun sendEffect(effect: UI_EFFECT) {
        viewModelScope.launch {
            _viewEffect.emit(effect)
        }
    }

    fun changeAppTopBarTitle(title: String) {
        _appState.value = AppStateFlow.TopBarTitle(title)
    }

    fun showSnackBar(
        messageId: Int,
        status: SnackBarState.States = SnackBarState.States.Success,
        duration: Long = 1500L
    ) {
        if (messageId != 0) {
            snackBarController.showSnackBar(
                messageId = messageId,
                status = status,
                duration = duration,
                scope = viewModelScope
            )
        }
    }

    fun showSnackBar(
        message: String,
        status: SnackBarState.States = SnackBarState.States.Success,
        duration: Long = 1500L
    ) {
        if (message.isNotBlank()) {
            snackBarController.showSnackBar(
                message = message,
                status = status,
                duration = duration,
                scope = viewModelScope
            )
        }
    }

    fun hideSnackBar() {
        snackBarController.hideSnackBar()
    }
}
