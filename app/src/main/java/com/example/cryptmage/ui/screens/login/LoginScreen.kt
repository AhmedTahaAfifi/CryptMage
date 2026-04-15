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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.ui.component.appProgressIndicator.AppProgressIndicator
import com.example.cryptmage.ui.component.appButton.AppButton
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
    viewModel: LoginViewModel = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current

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
    interaction: LoginInteraction
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(50.sdp),
                    painter = painterResource(R.drawable.logo_cryptmage),
                    contentDescription = stringResource(R.string.logo_crypt_mage)
                )

                Column {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = Color.White,
                        style = MyAppTypography.headlineSmall
                    )
                    Text(
                        text = stringResource(R.string.app_description),
                        color = appDescriptionTextColor,
                        style = MyAppTypography.labelSmall
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Border2
            )

            Spacer(modifier = Modifier.height(32.sdp))

            val title = if (viewState.isVaultCreated)
                stringResource(R.string.unlock_vault)
            else
                stringResource(R.string.login_title)
            val description = if (viewState.isVaultCreated)
                stringResource(R.string.enter_your_password_description)
            else
                stringResource(R.string.login_description)

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.sdp),
            ) {
                IconContainer(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.fillMaxWidth().size(24.sdp))

                // Login Description
                Column {
                    Text(
                        text = title,
                        color = Text1,
                        style = MyAppTypography.headlineSmall
                    )
                    Spacer(modifier = Modifier.padding(6.sdp))
                    Text(
                        text = description,
                        color = Text2,
                        style = MyAppTypography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.height(28.sdp))

                AppPasswordField(
                    value = viewState.masterPassword,
                    onValueChange = interaction::onMasterPasswordChange,
                    visible = viewState.isMasterPasswordVisible,
                    onToggleVisibility = interaction::onMasterPasswordClick,
                    label = stringResource(R.string.master_password),
                    placeHolder = stringResource(R.string.password_placeholder),
                )

                Spacer(modifier = Modifier.height(8.sdp))

                if (viewState.masterPassword.isNotEmpty()) {
                    PasswordStrengthIndicatorInline(
                        modifier = Modifier.fillMaxWidth(),
                        password = viewState.masterPassword
                    )
                }

                if (!viewState.isVaultCreated) {
                    Spacer(modifier = Modifier.height(16.sdp))

                    AppPasswordField(
                        value = viewState.confirmPassword,
                        onValueChange = interaction::onConfirmPasswordChange,
                        visible = viewState.isConfirmPasswordVisible,
                        onToggleVisibility = interaction::onConfirmPasswordClick,
                        label = stringResource(R.string.confirm_password),
                        placeHolder = stringResource(R.string.password_placeholder)
                    )
                }
            }
        }

        AppButton(
            modifier = Modifier.padding(horizontal = 16.sdp),
            onClick = interaction::onLogin,
            enabled = !viewState.isLoading,
            text =
            if (viewState.isVaultCreated)
                stringResource(R.string.unlock_vault)
            else
                stringResource(R.string.create_vault),
        )
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