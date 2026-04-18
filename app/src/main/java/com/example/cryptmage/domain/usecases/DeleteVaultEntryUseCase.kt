package com.example.cryptmage.domain.usecases

import com.example.cryptmage.data.repository.VaultRepository

class DeleteVaultEntryUseCase(private val repository: VaultRepository) {

    suspend operator fun invoke(vaultId: Int) {
        repository.deleteEntryById(vaultId)
    }

}