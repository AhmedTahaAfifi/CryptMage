package com.example.cryptmage.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptmage.data.models.VaultEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {

    @Query("SELECT * FROM vault_entries")
    fun getAllVaultEntries(): Flow<List<VaultEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: VaultEntry)

    @Delete
    suspend fun deleteVaultEntry(entry: VaultEntry)

    @Query("SELECT * FROM vault_entries Where id = :id")
    suspend fun getVaultEntryById(id: Int): VaultEntry?
}