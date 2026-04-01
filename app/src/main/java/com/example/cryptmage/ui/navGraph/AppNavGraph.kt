package com.example.cryptmage.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cryptmage.ui.screens.login.LoginScreen

@Composable
fun AppNavGraph(
    startDestination: AppRoute,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginRoute()
        generatePasswordRoute()
        homeRoute()
    }
}

val AppNavController = staticCompositionLocalOf<NavController> {
    error("No nav controller provided")
}


