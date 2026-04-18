package com.example.cryptmage.data.repository

import com.example.cryptmage.data.database.AppDataBase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager {
    private val _database = MutableStateFlow<AppDataBase?>(null)
    val databaseFlow = _database.asStateFlow()

    var database: AppDataBase?
        get() = _database.value
        set(value) {
            _database.value = value
        }

    fun isUnlocked() = database != null

    fun lock() {
        database?.close()
        database = null
    }

}