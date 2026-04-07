package com.example.cryptmage.data.repository

import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.moudels.VaultData
import kotlinx.coroutines.flow.Flow

class VaultRepositoryImpl(private val vaultDao: VaultDao): VaultRepository {

    override fun getAllVaultEntries(): Flow<List<VaultData>> {
        return vaultDao.getAllVaultEntries()
    }

    override suspend fun insert(entry: VaultData) {
        return vaultDao.insert(entry)
    }

    override suspend fun getEntry(vaultId: Int): VaultData? {
        return vaultDao.getVaultEntryById(vaultId)
    }

    override suspend fun delete(entry: VaultData) {
        return vaultDao.deleteVaultEntry(entry)
    }

}