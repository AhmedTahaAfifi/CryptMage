package com.example.cryptmage.ui.component.snackbar

data class SnackBarState(
    val messageId: Int = 0,
    val state: States = States.Success,
    val isVisible: Boolean = false
) {
    enum class States {
        Error,
        Success
    }
}
