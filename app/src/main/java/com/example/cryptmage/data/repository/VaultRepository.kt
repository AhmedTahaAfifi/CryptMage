package com.example.cryptmage.data.repository

import com.example.cryptmage.data.models.VaultEntry
import kotlinx.coroutines.flow.Flow

interface VaultRepository {

    fun getAllVaultEntries(): Flow<List<VaultEntry>>
    suspend fun insert(entry: VaultEntry)
    suspend fun update(entry: VaultEntry)
    suspend fun getEntry(vaultId: Int): Flow<VaultEntry?>
    suspend fun delete(entry: VaultEntry)
    suspend fun deleteEntryById(id: Int)
    suspend fun getEntryCount(): Int

}