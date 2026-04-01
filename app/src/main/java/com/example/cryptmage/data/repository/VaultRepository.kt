package com.example.cryptmage.data.repository

import com.example.cryptmage.data.moudels.VaultData
import kotlinx.coroutines.flow.Flow

interface VaultRepository {

    fun getAllVaultEntries(): Flow<List<VaultData>>
    suspend fun insert(entry: VaultData)
    suspend fun delete(entry: VaultData)

}