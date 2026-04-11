package com.example.cryptmage.ui.component.appTopBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import com.example.cryptmage.ui.navGraph.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    destination: NavDestination?,
    onNavigateUp: () -> Unit
) {
    val title = when (destination?.route) {
        AppRoute.Home::class.qualifiedName -> "Vault"
        AppRoute.GeneratePassword::class.qualifiedName -> "Generate Password"
        //AppRoute.Login::class.qualifiedName -> "Login"
        else -> "CryptMage"
    }

    val showBackButton = destination?.route != AppRoute.Home::class.qualifiedName &&
            destination?.route != AppRoute.Login::class.qualifiedName

    TopAppBar(
        title = {
            // TODO: Add app logo icon before title
            Text(title)
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}