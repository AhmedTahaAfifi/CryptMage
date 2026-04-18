package com.example.cryptmage.domain.usecases

import com.example.cryptmage.data.models.VaultEntry
import com.example.cryptmage.data.repository.VaultRepository
import kotlinx.coroutines.flow.Flow

class GetVaultEntryUseCase(private val repository: VaultRepository) {

    suspend operator fun invoke(vaultId: Int): Flow<VaultEntry?> {
        return repository.getEntry(vaultId)
    }

}