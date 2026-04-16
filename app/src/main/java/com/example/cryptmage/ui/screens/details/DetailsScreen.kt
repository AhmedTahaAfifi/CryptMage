package com.example.cryptmage.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.OutlinedButtonVariant
import com.example.cryptmage.ui.component.appOutlineButton.AppOutlinedButton
import com.example.cryptmage.ui.component.appProgressIndicator.AppProgressIndicator
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.LocalTopBarConfig
import com.example.cryptmage.ui.navGraph.model.AppTopBarConfig
import com.example.cryptmage.ui.screens.details.component.DetailsFieldGroup
import com.example.cryptmage.ui.screens.login.components.IconContainer
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Text1
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(viewModel: DetailsViewModel = koinViewModel()) {
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current
    val topBarConfig = LocalTopBarConfig.current

    LaunchedEffect(Unit) {
        topBarConfig.value = AppTopBarConfig(
            titleId = R.string.details_title,
            iconId = R.drawable.ic_edit,
            iconContentDescriptionId = R.string.icon_edit,
            onIconClick = {
                navController.navigate(AppRoute.GeneratePassword(vaultId = uiState.vaultId))
            }
        )
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is DetailsEffect.VaultDeleted -> navController.navigateUp()
                else -> {}
            }
        }
    }
    
    DetailsScreenContent(
        uiState = uiState,
        interaction = viewModel
    )

    if (uiState.isLoading) {
        AppProgressIndicator()
    }
}


@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    uiState: DetailsUIState,
    interaction: DetailsInteraction,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.sdp, start = 16.sdp, end = 16.sdp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconContainer(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(8.sdp))
                Text(
                    text = uiState.vaultName,
                    color = Text1,
                    style = MyAppTypography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(13.sdp))
            DetailsFieldGroup(
                email = uiState.email,
                password = uiState.password,
                lastUpdated = uiState.lastUpdated,
                lastUpdatedTimestamp = uiState.lastUpdatedTimestamp,
                isPasswordVisible = uiState.isPasswordVisible,
                isPasswordCopied = uiState.isPasswordCopied,
                isEmailCopied = uiState.isEmailCopied,
                onCopyPasswordToggled = { interaction.onPasswordCopiedClick(uiState.password) },
                onShowPasswordToggled = interaction::onPasswordClick,
                onEmailToggled = { interaction.onEmailCopiedClick(uiState.email) }
            )
        }

        Spacer(modifier = Modifier.height(16.sdp)) // Spacer before the button

        AppOutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.sdp),
            text = stringResource(R.string.delete),
            onClick = interaction::onDelete,
            variant = OutlinedButtonVariant.DANGER
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenContentPrev() {
    /*DetailsScreenContent(
        uiState = DetailsUIState(
            vaultName = "GitHub",
            email = "william.henry.harrison@example-pet-store.com",
            password = "123456789",
            lastUpdated = "2 days ago",
            isPasswordVisible = false
        ),
    )*/
}
