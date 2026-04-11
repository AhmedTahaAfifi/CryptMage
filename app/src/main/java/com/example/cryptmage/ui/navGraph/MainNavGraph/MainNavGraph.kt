package com.example.cryptmage.ui.navGraph.MainNavGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.generatePasswordRoute
import com.example.cryptmage.ui.navGraph.homeRoute
import com.example.cryptmage.ui.navGraph.loginRoute

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    startDestination: AppRoute,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeRoute()
        generatePasswordRoute()
    }
}

