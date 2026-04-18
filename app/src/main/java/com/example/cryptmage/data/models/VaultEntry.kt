package com.example.cryptmage.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptmage.data.enums.PasswordStrength

@Entity(tableName = "vault_entries")
data class VaultEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    @ColumnInfo(name = "password_strength_slug")
    val passwordStrengthSlug: PasswordStrength? = null,
    val lastUpdated: Long? = null,
    val passwordLength: Int = 20,
    val includeUpperCase: Boolean = true,
    val includeNumbers: Boolean = true,
    val includeSymbols: Boolean = true,
    val avoidAmbiguous: Boolean = false,
)
