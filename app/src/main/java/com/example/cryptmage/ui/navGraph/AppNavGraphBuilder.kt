package com.example.cryptmage.ui.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cryptmage.ui.screens.details.DetailsScreen
import com.example.cryptmage.ui.screens.generatePassword.GeneratePasswordScreen
import com.example.cryptmage.ui.screens.home.HomeScreen
import com.example.cryptmage.ui.screens.login.LoginScreen

// Login
fun NavGraphBuilder.loginRoute() {
    composable<AppRoute.Login> {
        LoginScreen()
    }
}

fun NavGraphBuilder.homeRoute() {
    composable<AppRoute.Home> {
        HomeScreen()
    }
}

fun NavGraphBuilder.generatePasswordRoute() {
    composable<AppRoute.GeneratePassword> {
        GeneratePasswordScreen()
    }
}

fun NavGraphBuilder.detailsRoute() {
    composable<AppRoute.Details> {
        DetailsScreen()
    }
}