package com.example.cryptmage.ui.screens.login

data class LoginUIState(
    val masterPassword: String = "",
    val isMasterPasswordVisible: Boolean = false,
    val confirmPassword: String = "",
    val isConfirmPasswordVisible: Boolean = false,
)
