package com.example.cryptmage.data.moudels

import com.example.cryptmage.data.enums.PasswordStrengthSlug

data class VaultData(
    val id: Int? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    val passwordStrength: String? = null,
    val passwordStrengthSlug: PasswordStrengthSlug? = null
)
