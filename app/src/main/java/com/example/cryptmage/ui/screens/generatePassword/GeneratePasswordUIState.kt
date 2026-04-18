package com.example.cryptmage.ui.screens.generatePassword

import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.models.VaultEntry

data class GeneratePasswordUIState(
    val id: Int = 0,
    val password: String = "",
    val vaultName: String = "",
    val email: String = "",
    val lastUpdated: Long? = null,
    val length: Int = 20,
    val upperCase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidAmbiguous: Boolean = false,
    val passwordStrengthSlug: PasswordStrength = PasswordStrength.WEAK,
    val vaultNameError: Int? = null,
    val emailError: Int? = null,
    val isEditMode: Boolean = false
) {
    fun toData() = VaultEntry(
        id = id,
        name = vaultName,
        email = email,
        password = password,
        passwordStrengthSlug = passwordStrengthSlug,
        lastUpdated = lastUpdated,
        passwordLength = length,
        includeUpperCase = upperCase,
        includeNumbers = numbers,
        includeSymbols = symbols,
        avoidAmbiguous = avoidAmbiguous
    )


}

fun VaultEntry.toUi() = GeneratePasswordUIState(
    id = id,
    vaultName = name ?: "",
    email = email ?: "",
    password = password ?: "",
    passwordStrengthSlug = passwordStrengthSlug ?: PasswordStrength.WEAK,
    lastUpdated = lastUpdated,
    isEditMode = true,
    length = passwordLength,
    upperCase = includeUpperCase,
    numbers = includeNumbers,
    symbols = includeSymbols,
    avoidAmbiguous = avoidAmbiguous
)
