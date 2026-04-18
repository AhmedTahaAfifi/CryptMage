package com.example.cryptmage.domain.usecases

import com.example.cryptmage.data.models.VaultEntry
import com.example.cryptmage.data.repository.VaultRepository

class InsertVaultEntryUseCase(private val repository: VaultRepository) {

    suspend operator fun invoke(entry: VaultEntry) {
        return repository.insert(entry)
    }

}