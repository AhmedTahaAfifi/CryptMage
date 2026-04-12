package com.example.cryptmage.ui.screens.home

import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.models.VaultEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUIState (
    val vaultEntries: Flow<List<VaultEntryUiState>> = emptyFlow()
)

data class VaultEntryUiState(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val passwordStrengthSlug: PasswordStrength
) {
    companion object {
        fun VaultEntry.toUi() = VaultEntryUiState(
            id = id,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            passwordStrengthSlug = passwordStrengthSlug ?: PasswordStrength.WEAK
        )
    }
}