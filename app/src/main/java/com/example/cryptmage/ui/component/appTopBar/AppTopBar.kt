package com.example.cryptmage.ui.component.appTopBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.cryptmage.R
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.LocalTopBarConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    destination: NavDestination?,
    onNavigateUp: () -> Unit
) {
    val config by LocalTopBarConfig.current
    /*val title = when (destination?.route) {
        AppRoute.Login::class.qualifiedName -> ""
        AppRoute.Home::class.qualifiedName -> stringResource(R.string.home_title)
        AppRoute.GeneratePassword::class.qualifiedName -> stringResource(R.string.generate_password_title)
        AppRoute.Details::class.qualifiedName -> stringResource(R.string.details_title)
        else -> "CryptMage"
    }*/

    val showBackButton = destination?.route != AppRoute.Home::class.qualifiedName &&
            destination?.route != AppRoute.Login::class.qualifiedName

    TopAppBar(
        title = {
            // TODO: Add app logo icon before title
            Text(stringResource(config?.titleId ?: R.string.app_name))
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
        },
        actions = {
            if (config?.iconId != null) {
                IconButton(onClick = {config?.onIconClick?.invoke()}) {
                    Icon(
                        painter = painterResource(config!!.iconId!!),
                        contentDescription = stringResource(config!!.iconContentDescriptionId ?: "".toInt())
                    )
                }
            }
        }
    )
}