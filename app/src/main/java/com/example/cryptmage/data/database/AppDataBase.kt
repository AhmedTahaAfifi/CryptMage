package com.example.cryptmage.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.moudels.VaultData

@Database(
    entities = [VaultData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun vaultDao(): VaultDao

}