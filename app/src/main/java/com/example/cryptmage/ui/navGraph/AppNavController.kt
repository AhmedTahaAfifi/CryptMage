package com.example.cryptmage.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

val AppNavController = staticCompositionLocalOf<NavController> {
    error("No nav controller provided")
}

@Composable
fun isCurrentDestination(navController: NavController, route: AppRoute): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    return navBackStackEntry?.destination?.route == route::class.qualifiedName
}