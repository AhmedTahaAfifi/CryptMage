package com.example.cryptmage.data.repository

import com.example.cryptmage.data.dao.VaultDao
import com.example.cryptmage.data.models.VaultEntry
import kotlinx.coroutines.flow.Flow

class VaultRepositoryImpl(private val vaultDao: VaultDao): VaultRepository {

    override fun getAllVaultEntries(): Flow<List<VaultEntry>> {
        return vaultDao.getAllVaultEntries()
    }

    override suspend fun insert(entry: VaultEntry) {
        return vaultDao.insert(entry.copy(lastUpdated = System.currentTimeMillis()))
    }

    override suspend fun update(entry: VaultEntry) {
        return vaultDao.update(entry)
    }

    override suspend fun getEntry(vaultId: Int): Flow<VaultEntry?> {
        return vaultDao.getVaultEntryById(vaultId)
    }

    override suspend fun delete(entry: VaultEntry) {
        return vaultDao.deleteVaultEntry(entry)
    }

    override suspend fun deleteEntryById(id: Int) {
        return vaultDao.deleteVaultEntry(VaultEntry(id = id))
    }
}