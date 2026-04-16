package com.example.cryptmage.ui.navGraph

import kotlinx.serialization.Serializable

sealed interface AppRoute {

    @Serializable
    data object Login: AppRoute {
    }

    @Serializable
    data object Home: AppRoute

    @Serializable
    data class GeneratePassword(val vaultId: Int? = null): AppRoute {
    }

    @Serializable
    data class Details(val vaultId: Int): AppRoute

}