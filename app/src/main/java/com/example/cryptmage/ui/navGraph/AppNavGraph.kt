package com.example.cryptmage.ui.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: AppRoute,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginRoute()
        homeRoute()
        generatePasswordRoute()
    }
}

