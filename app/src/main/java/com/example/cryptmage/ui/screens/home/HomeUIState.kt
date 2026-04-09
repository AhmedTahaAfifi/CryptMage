package com.example.cryptmage.ui.screens.home

import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.moudels.VaultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUIState (
    val vaultData: Flow<List<VaultDataUiState>> = emptyFlow()
)

data class VaultDataUiState(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val passwordStrengthSlug: PasswordStrength
) {
    companion object {
        fun VaultData.toUi() = VaultDataUiState(
            id = id,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            passwordStrengthSlug = passwordStrengthSlug ?: PasswordStrength.WEAK
        )
    }
}