package com.example.cryptmage.ui.screens.generatePassword

data class GeneratePasswordUIState(
    val password: String = "",
    val length: Int = 20,
    val upperCase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidAmbiguous: Boolean = false,
    val vaultName: String = "",
    val email: String = ""
)
