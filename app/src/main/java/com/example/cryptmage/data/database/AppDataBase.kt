package com.example.cryptmage.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.models.VaultEntry

@Database(
    entities = [VaultEntry::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun vaultDao(): VaultDao

}