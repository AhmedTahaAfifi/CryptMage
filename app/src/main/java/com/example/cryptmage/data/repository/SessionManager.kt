package com.example.cryptmage.data.repository

import android.content.Context
import com.example.cryptmage.data.database.AppDataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import com.example.cryptmage.utils.Constants

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("cryptmage_prefs", Context.MODE_PRIVATE)
    private val bioPrefs = context.getSharedPreferences("bio_prefs", Context.MODE_PRIVATE)
    private val _database = MutableStateFlow<AppDataBase?>(null)
    val databaseFlow = _database.asStateFlow()

    var database: AppDataBase?
        get() = _database.value
        set(value) {
            _database.value = value
        }

    fun isUnlocked() = database != null

    fun lock() {
        this.database?.close()
        this.database = null
    }

    fun saveUserEmail(email: String?) {
        this.prefs.edit { putString(Constants.Prefs.DRIVE_USER_EMAIL, email) }
    }

    fun getUserEmail(): String? {
        return this.prefs.getString(Constants.Prefs.DRIVE_USER_EMAIL, null)
    }

    fun clearUserEmail() {
        prefs.edit { remove(Constants.Prefs.DRIVE_USER_EMAIL) }
    }

    fun isBiometricEnabled(): Boolean {
        return bioPrefs.getString(Constants.Prefs.ENCRYPTED_PASSWORD, null) != null
    }

    fun saveBiometricData(encryptedPassword: String, iv: String) {
        bioPrefs.edit {
            putString(Constants.Prefs.ENCRYPTED_PASSWORD, encryptedPassword)
            putString(Constants.Prefs.IV, iv)
        }
    }

    fun getBiometricEncryptedPassword(): String? {
        return bioPrefs.getString(Constants.Prefs.ENCRYPTED_PASSWORD, null)
    }

    fun getBiometricIv(): String? {
        return bioPrefs.getString(Constants.Prefs.IV, null)
    }

    fun clearBiometricData() {
        bioPrefs.edit {
            remove(Constants.Prefs.ENCRYPTED_PASSWORD)
            remove(Constants.Prefs.IV)
        }
    }
}