package com.example.cryptmage.ui.component.appTopBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.cryptmage.R
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.LocalTopBarConfig
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Surface3
import com.example.cryptmage.ui.theme.Text1
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    destination: NavDestination?,
    onNavigateUp: () -> Unit
) {
    val config by LocalTopBarConfig.current

    val showBackButton = destination?.route != AppRoute.Home::class.qualifiedName &&
            destination?.route != AppRoute.Login::class.qualifiedName

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (destination?.route == AppRoute.Home::class.qualifiedName) {
                    Image(
                        modifier = Modifier.size(40.sdp).padding(end = 6.sdp),
                        painter = painterResource(R.drawable.logo_cryptmage),
                        contentDescription = stringResource(R.string.logo_crypt_mage)
                    )
                }
                Text(
                    text = stringResource(config?.titleId ?: R.string.app_name),
                    color = Text1,
                    style = MyAppTypography.headlineSmall
                )
            }
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
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 12.sdp)
                        .size(20.sdp)
                        .background(Surface2, RoundedCornerShape(7.sdp))
                        .border(1.sdp, Surface3, RoundedCornerShape(7.sdp)),
                    onClick = {config?.onIconClick?.invoke()}
                ) {
                    Icon(
                        modifier = Modifier.size(15.sdp),
                        painter = painterResource(config!!.iconId!!),
                        contentDescription = config?.iconContentDescriptionId?.let { stringResource(it) }
                    )
                }
            }
        }
    )
}