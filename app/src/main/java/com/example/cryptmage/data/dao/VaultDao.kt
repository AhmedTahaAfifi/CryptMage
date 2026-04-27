package com.example.cryptmage.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptmage.data.models.VaultEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {

    @Query("SELECT * FROM vault_entries")
    fun getAllVaultEntries(): Flow<List<VaultEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: VaultEntry)

    @Update
    suspend fun update(entry: VaultEntry)

    @Delete
    suspend fun deleteVaultEntry(entry: VaultEntry)

    @Query("SELECT * FROM vault_entries Where id = :id")
    fun getVaultEntryById(id: Int): Flow<VaultEntry?>

    @Query("SELECT COUNT(*) FROM vault_entries")
    suspend fun getEntryCount(): Int
}