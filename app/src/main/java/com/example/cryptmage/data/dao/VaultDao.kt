package com.example.cryptmage.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptmage.data.moudels.VaultData
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {

    @Query("SELECT * FROM vault_entries")
    fun getAllVaultEntries(): Flow<List<VaultData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: VaultData)

    @Delete
    suspend fun deleteVaultEntry(entry: VaultData)

    @Query("SELECT * FROM vault_entries Where id = :id")
    suspend fun getVaultEntryById(id: Int): VaultData?
}