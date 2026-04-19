package com.example.cryptmage.ui.screens.cloudSync

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.OutlinedButtonVariant
import com.example.cryptmage.ui.component.appButton.AppButton
import com.example.cryptmage.ui.component.appOutlineButton.AppOutlinedButton
import com.example.cryptmage.ui.component.appProgressIndicator.AppProgressIndicator
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.LocalTopBarConfig
import com.example.cryptmage.ui.navGraph.isCurrentDestination
import com.example.cryptmage.ui.navGraph.model.AppTopBarConfig
import com.example.cryptmage.ui.screens.cloudSync.components.ProviderCard
import com.example.cryptmage.ui.screens.cloudSync.components.StatCard
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Text1
import com.example.cryptmage.ui.theme.Text3
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun CloudSyncScreen(
    viewModel: CloudSyncViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val topBarConfig = LocalTopBarConfig.current
    val navController = AppNavController.current
    val isCurrentDistinction = isCurrentDestination(navController, AppRoute.CloudSync)

    // 1. Launcher to handle the Google Drive Permission Consent Screen
    val authorizationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Permission granted! Tell the ViewModel to finish the link
            viewModel.onDrivePermissionGranted(uiState.userEmail)
        }
    }

    LaunchedEffect(isCurrentDistinction) {
        if (isCurrentDistinction) {
            // Set TopBar Title
            topBarConfig.value = AppTopBarConfig(
                titleId = R.string.cloud_sync_title
            )

            // 2. Listen for "Show Consent Popup" effects from the ViewModel
            viewModel.viewEffect.collect { effect ->
                when (effect) {
                    is CloudSyncEffect.RequestDrivePermission -> {
                        authorizationLauncher.launch(
                            IntentSenderRequest.Builder(effect.intentSender).build()
                        )
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CloudSyncContent(
            uiState = uiState,
            interaction = viewModel
        )

        // Loading Overlay
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                AppProgressIndicator()
            }
        }
    }
}

@Composable
private fun CloudSyncContent(
    uiState: CloudSyncUIState,
    interaction: CloudSyncInteraction
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- THE MAIN STATUS CARD ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface2, RoundedCornerShape(24.sdp))
                .padding(24.sdp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Circular Sync Icon
                Box(
                    modifier = Modifier
                        .size(80.sdp)
                        .border(2.dp, Accent.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Sync,
                        contentDescription = null,
                        modifier = Modifier.size(32.sdp),
                        tint = Accent
                    )
                }

                Spacer(modifier = Modifier.height(16.sdp))
                Text(
                    text = stringResource(id = R.string.vault_synced),
                    style = MyAppTypography.headlineSmall,
                    color = Text1
                )
                Text(
                    text = "${uiState.lastSyncTime} • ${uiState.entryCount} entries • ${uiState.vaultSize}",
                    style = MyAppTypography.labelMedium,
                    color = Text3
                )

                Spacer(modifier = Modifier.height(24.sdp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    StatCard(
                        label = stringResource(id = R.string.entries),
                        value = "${uiState.entryCount}",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(id = R.string.vault_size),
                        value = uiState.vaultSize,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(id = R.string.backups),
                        value = "${uiState.backupCount}",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.sdp))

                // Providers Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    ProviderCard(
                        name = "Drive",
                        status = if (uiState.isDriveConnected) "connected" else "tap to link",
                        isConnected = uiState.isDriveConnected,
                        onClick = {
                            if (!uiState.isDriveConnected) {
                                interaction.onLinkDriveClick(context)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    ProviderCard(
                        name = "Dropbox",
                        status = "tap to link",
                        isConnected = false,
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                    ProviderCard(
                        name = "OneDrive",
                        status = "tap to link",
                        isConnected = false,
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- ACTION BUTTONS ---
        AppButton(
            text = stringResource(id = R.string.sync_now),
            onClick = interaction::onSyncClick,
            enabled = uiState.isDriveConnected && !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(12.sdp))

        AppOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.export_encrypted_backup),
            onClick = {},
            variant = OutlinedButtonVariant.EXPORT
        )
    }
}
