package com.example.cryptmage.ui.navGraph

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val AppNavController = staticCompositionLocalOf<NavController> {
    error("No nav controller provided")
}