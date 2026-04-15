package com.example.cryptmage.ui.screens.details
sealed class DetailsEffect {
    data object VaultDeleted : DetailsEffect()
    data object NavigateUp : DetailsEffect()
}