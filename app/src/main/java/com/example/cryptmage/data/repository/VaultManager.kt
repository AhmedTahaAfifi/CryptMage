package com.example.cryptmage.data.repository

import android.content.Context
import com.example.cryptmage.utils.keyDerivation.KeyDerivationUtil

class VaultManager(private val context: Context) {

    fun isVaultCreated(): Boolean {
        return KeyDerivationUtil.getSalt(context) != null
    }

    fun getSalt(): ByteArray? {
        return KeyDerivationUtil.getSalt(context)
    }

    fun generateNewSalt(): ByteArray {
        return KeyDerivationUtil.generateAndSaveSalt(context)
    }

    fun clearVault() {
        KeyDerivationUtil.clearSalt(context)
    }
}