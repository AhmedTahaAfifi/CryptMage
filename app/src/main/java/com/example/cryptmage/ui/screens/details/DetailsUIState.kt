package com.example.cryptmage.ui.screens.details

import com.example.cryptmage.data.models.VaultEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DetailsUIState(
    val vaultId: Int = 0,
    val vaultName: String = "",
    val email: String = "",
    val password: String = "",
    val lastUpdatedTimestamp: Long? = null,
    val lastUpdated: String = "",
    val isPasswordVisible: Boolean = false,
    val isPasswordCopied: Boolean = false,
    val isEmailCopied: Boolean = false,
    val isLoading: Boolean = false,
) {
    fun toData() = VaultEntry(
        id = vaultId,
        name = vaultName,
        email = email,
        password = password
        // lastUpdated is not set here as it's updated on insert/update in repository
    )
}

fun VaultEntry.toUi(isLoading: Boolean = false) = DetailsUIState(
    vaultId = id,
    vaultName = name ?: "",
    email = email ?: "",
    password = password ?: "",
    isLoading = isLoading,
    lastUpdatedTimestamp = lastUpdated,
    lastUpdated = lastUpdated?.let { timestamp ->
        SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(timestamp))
    } ?: ""
)
