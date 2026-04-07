package com.example.cryptmage.data.moudels.generatePassword

data class GeneratePasswordData(
    val password: String = "",
    val length: Int = 20,
    val upperCase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidAmbiguous: Boolean = false
)
