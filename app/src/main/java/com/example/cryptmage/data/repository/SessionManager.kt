package com.example.cryptmage.data.repository

import com.example.cryptmage.data.database.AppDataBase

class SessionManager {
    var database: AppDataBase? = null

    fun isUnlocked() = database != null

    fun lock() {
        database?.close()
        database = null
    }

}