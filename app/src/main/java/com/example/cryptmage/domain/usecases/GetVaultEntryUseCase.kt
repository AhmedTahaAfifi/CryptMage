package com.example.cryptmage.domain.usecases

import com.example.cryptmage.data.models.VaultEntry
import com.example.cryptmage.data.repository.VaultRepository

class GetVaultEntryUseCase(private val repository: VaultRepository) {

    suspend operator fun invoke(vaultId: Int): VaultEntry? {
        return repository.getEntry(vaultId)
    }

}