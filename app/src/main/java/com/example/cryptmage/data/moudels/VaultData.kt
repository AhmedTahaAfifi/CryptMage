package com.example.cryptmage.data.moudels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptmage.data.enums.PasswordStrength

@Entity(tableName = "vault_entries")
data class VaultData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null,
    val passwordStrengthId: Int = 0,
    @ColumnInfo(name = "password_strength_slug")
    val passwordStrengthSlug: PasswordStrength? = null
)
