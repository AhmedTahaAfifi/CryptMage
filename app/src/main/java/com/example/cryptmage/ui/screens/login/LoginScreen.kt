package com.example.cryptmage.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.OutlinedButtonVariant
import com.example.cryptmage.ui.component.appProgressIndicator.AppProgressIndicator
import com.example.cryptmage.ui.component.appButton.AppButton
import com.example.cryptmage.ui.component.appOutlineButton.AppOutlinedButton
import com.example.cryptmage.ui.component.appPasswordField.AppPasswordField
import com.example.cryptmage.ui.component.passwordStrengthIndicatorInline.PasswordStrengthIndicatorInline
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.screens.login.components.IconContainer
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Text1
import com.example.cryptmage.ui.theme.Text2
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current
    val context = LocalContext.current

    LaunchedEffect(viewState.isBiometricEnabled) {
        if (viewState.isBiometricEnabled) {
            viewModel.onBiometricLogin(context as FragmentActivity)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when(effect) {
                is LoginEffect.NavigateToHome -> {
                    navController.navigate(AppRoute.Home) {
                        popUpTo(AppRoute.Login) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    LoginContent(
        modifier = modifier,
        viewState = viewState,
        interaction = viewModel
    )


    if (viewState.isLoading) {
        AppProgressIndicator()
    }

}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    viewState: LoginUIState,
    interaction: LoginInteraction,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            LoginHeader()

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Border2,
            )

            Spacer(modifier = Modifier.height(32.sdp))

            LoginForm(
                viewState = viewState,
                interaction = interaction,
            )
        }

        LoginFooter(
            viewState = viewState,
            interaction = interaction,
        )
    }
}

@Composable
private fun LoginHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.sdp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(50.sdp),
            painter = painterResource(R.drawable.logo_cryptmage),
            contentDescription = stringResource(R.string.logo_crypt_mage),
        )

        Column {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.White,
                style = MyAppTypography.headlineSmall,
            )
            Text(
                text = stringResource(R.string.app_description),
                color = appDescriptionTextColor,
                style = MyAppTypography.labelSmall,
            )
        }
    }
}

@Composable
private fun LoginForm(
    viewState: LoginUIState,
    interaction: LoginInteraction,
) {
    val title = when {
        viewState.isVaultCreated -> stringResource(R.string.unlock_vault)
        viewState.isImportMode -> stringResource(R.string.import_vault)
        else -> stringResource(R.string.login_title)
    }
    val description = when {
        viewState.isVaultCreated -> stringResource(R.string.enter_your_password_description)
        viewState.isImportMode -> stringResource(R.string.enter_your_password_description)
        else -> stringResource(R.string.login_description)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.sdp),
    ) {
        IconContainer(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.fillMaxWidth().size(24.sdp))

        Column {
            Text(
                text = title,
                color = Text1,
                style = MyAppTypography.headlineSmall,
            )
            Spacer(modifier = Modifier.padding(6.sdp))
            Text(
                text = description,
                color = Text2,
                style = MyAppTypography.labelMedium,
            )
        }
        Spacer(modifier = Modifier.height(28.sdp))

        if (!viewState.isImportMode || viewState.isBackupDownloaded) {
            AppPasswordField(
                value = viewState.masterPassword,
                onValueChange = interaction::onMasterPasswordChange,
                visible = viewState.isMasterPasswordVisible,
                onClick = interaction::onMasterPasswordClick,
                label = stringResource(R.string.master_password),
                placeHolder = stringResource(R.string.password_placeholder),
            )
        }

        Spacer(modifier = Modifier.height(8.sdp))

        if (viewState.masterPassword.isNotEmpty() && !viewState.isImportMode) {
            PasswordStrengthIndicatorInline(
                modifier = Modifier.fillMaxWidth(),
                password = viewState.masterPassword,
            )
        }

        if (!viewState.isVaultCreated && !viewState.isImportMode) {
            Spacer(modifier = Modifier.height(16.sdp))

            AppPasswordField(
                value = viewState.confirmPassword,
                onValueChange = interaction::onConfirmPasswordChange,
                visible = viewState.isConfirmPasswordVisible,
                onClick = interaction::onConfirmPasswordClick,
                label = stringResource(R.string.confirm_password),
                placeHolder = stringResource(R.string.password_placeholder),
            )
        }
    }
}

@Composable
private fun LoginFooter(
    viewState: LoginUIState,
    interaction: LoginInteraction,
) {
    val context = LocalContext.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.sdp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (viewState.isImportMode && !viewState.isBackupDownloaded) {
                        interaction.onImportVault(context as FragmentActivity)
                    } else {
                        interaction.onLogin()
                    }
                },
                enabled = !viewState.isLoading,
                text = when {
                    viewState.isVaultCreated && (!viewState.isImportMode || viewState.isBackupDownloaded) -> stringResource(R.string.unlock_vault)
                    viewState.isImportMode && !viewState.isBackupDownloaded -> "Connect Google Drive"
                    viewState.isImportMode -> "Restore & Unlock"
                    else -> stringResource(R.string.create_vault)
                },
            )

            if (viewState.isBiometricEnabled && viewState.isVaultCreated && (!viewState.isImportMode || viewState.isBackupDownloaded)) {
                Spacer(modifier = Modifier.size(8.sdp))
                BiometricButton(
                    onClick = { interaction.onBiometricLogin(context as FragmentActivity) },
                )
            }
        }

        if (!viewState.isVaultCreated) {
            Spacer(modifier = Modifier.height(8.sdp))
            AppOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.sdp),
                text = if (viewState.isImportMode) stringResource(R.string.back_to_create_vault) else stringResource(R.string.import_vault),
                onClick = interaction::onToggleImportMode,
                variant = OutlinedButtonVariant.EXPORT,
            )
        }

        Spacer(modifier = Modifier.height(16.sdp))
    }
}

@Composable
private fun BiometricButton(
    onClick: () -> Unit,
) {
    androidx.compose.material3.Card(
        modifier = Modifier.size(42.sdp),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = com.example.cryptmage.ui.theme.Accent),
        shape = RoundedCornerShape(10.sdp),
        onClick = onClick,
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Rounded.Fingerprint,
                contentDescription = "Biometric",
                tint = Color.White,
                modifier = Modifier.size(24.sdp),
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    backgroundColor = 0xFF0A0A0F,
)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}