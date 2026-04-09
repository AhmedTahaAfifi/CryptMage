package com.example.cryptmage.ui.screens.generatePassword

import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.moudels.VaultData

data class GeneratePasswordUIState(
    val password: String = "",
    val vaultName: String = "",
    val email: String = "",
    val length: Int = 20,
    val upperCase: Boolean = true,
    val numbers: Boolean = true,
    val symbols: Boolean = true,
    val avoidAmbiguous: Boolean = false,
    val passwordStrengthSlug: PasswordStrength = PasswordStrength.WEAK,
) {
    companion object {
        fun GeneratePasswordUIState.toData() = VaultData(
            name = vaultName,
            email = email,
            password = password,
            passwordStrengthSlug = passwordStrengthSlug
        )
    }
}
