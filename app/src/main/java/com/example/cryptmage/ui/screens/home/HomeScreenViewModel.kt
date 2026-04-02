package com.example.cryptmage.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.data.moudels.VaultData
import com.example.cryptmage.data.repository.VaultRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val vaultRepository: VaultRepository): ViewModel() {


    val vaultEntries = vaultRepository.getAllVaultEntries().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun insertData() {
        viewModelScope.launch {
            vaultRepository.insert(VaultData(
                name = "GitHub",
                email = "ahmedTest@gmail.com",
                passwordStrength = "Strong",
                passwordStrengthSlug = PasswordStrength.STRONG
            ))
        }
    }

}