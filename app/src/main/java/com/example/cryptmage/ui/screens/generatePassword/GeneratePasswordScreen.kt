package com.example.cryptmage.ui.screens.generatePassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.ui.component.appButton.AppButton
import com.example.cryptmage.ui.component.appTextField.AppTextField
import com.example.cryptmage.ui.component.generatedPasswordText.GeneratedPasswordText
import com.example.cryptmage.ui.component.ghostActionButton.GhostActionButton
import com.example.cryptmage.ui.component.passwordLengthSlider.PasswordLengthSlider
import com.example.cryptmage.ui.component.passwordStrengthIndicator.PasswordStrengthIndicator
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.LocalTopBarConfig
import com.example.cryptmage.ui.navGraph.isCurrentDestination
import com.example.cryptmage.ui.navGraph.model.AppTopBarConfig
import com.example.cryptmage.ui.screens.generatePassword.components.GeneratorToggleGroup
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import com.example.cryptmage.utils.ClipboardUtils
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun GeneratePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: GeneratePasswordViewModel = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current
    val topBarConfig = LocalTopBarConfig.current
    val isCurrentDistinction = isCurrentDestination(navController, AppRoute.GeneratePassword())

    LaunchedEffect(isCurrentDistinction, viewState.isEditMode) {
        topBarConfig.value = AppTopBarConfig(
            titleId = if (viewState.isEditMode) R.string.edit_password else R.string.generate_password_title
        )
    }

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is GeneratePasswordEffect.NavigateUp -> navController.navigateUp()
            }
        }
    }

    GeneratePasswordContent(
        modifier = modifier,
        viewState = viewState,
        interaction = viewModel
    )
}

@Composable
private fun GeneratePasswordContent(
    modifier: Modifier = Modifier,
    viewState: GeneratePasswordUIState,
    interaction: GeneratePasswordInteractionListener
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.sdp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp)
                .background(Surface2, RoundedCornerShape(12.sdp))
                .border(0.5.dp, Border2, RoundedCornerShape(12.sdp))
                .padding(vertical = 16.sdp, horizontal = 12.sdp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                GeneratedPasswordText(viewState.password)

                PasswordStrengthIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    password = viewState.password
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.sdp, Alignment.CenterHorizontally)
                ) {
                    GhostActionButton(label = stringResource(R.string.copy), onClick = {
                        ClipboardUtils.copyToClipboard(context, viewState.password)
                        interaction.onCopy()
                    })
                    GhostActionButton(label = stringResource(R.string.refresh), onClick = interaction::onRefresh)
                }
            }
        }

        PasswordLengthSlider(
            modifier = Modifier.padding(top = 20.sdp),
            length = viewState.length,
            onLengthChange = interaction::onLengthChange
        )

        GeneratorToggleGroup(
            upperCaseEnabled = viewState.upperCase,
            numbersEnabled = viewState.numbers,
            symbolsEnabled = viewState.symbols,
            avoidAmbiguousEnabled = viewState.avoidAmbiguous,
            onUpperCaseToggle = interaction::onToggleUpperCase,
            onNumbersToggle = interaction::onToggleNumbers,
            onSymbolsToggle = interaction::onToggleSymbols,
            onAvoidAmbiguousToggle = interaction::onToggleAvoidAmbiguous,
        )


        ExpandableDetailsSection(
            vaultName = viewState.vaultName,
            email = viewState.email,
            vaultNameError = viewState.vaultNameError,
            emailError = viewState.emailError,
            onVaultNameChange = interaction::onVaultNameChange,
            onEmailChange = interaction::onEmailChange
        )
        Spacer(modifier = Modifier.weight(1f))

        AppButton(
            modifier = Modifier.padding(vertical = 16.sdp),
            onClick = interaction::onSave,
            text = stringResource(
                if (viewState.isEditMode) R.string.update_password else R.string.save_to_vault
            )
        )
    }
}

@Composable
fun ExpandableDetailsSection(
    vaultName: String,
    email: String,
    vaultNameError: Int?,
    emailError: Int?,
    onVaultNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.sdp)) {
        // Section label
        Text(
            text = stringResource(R.string.save_as),
            color = appDescriptionTextColor,
            style = MyAppTypography.labelSmall,
        )

        // Name field
        AppTextField(
            value = vaultName,
            onValueChange = onVaultNameChange,
            labelId = R.string.vault_name,
            placeholderId = R.string.vault_name_place_holder,
            isRequired = true,
            isError = vaultNameError != null,
            errorId = vaultNameError
        )

        // Email field
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            labelId = R.string.text_field_email,
            placeholderId = R.string.email_place_holder,
            keyboardType = KeyboardType.Email,
            isRequired = true,
            isError = emailError != null,
            errorId = emailError
        )
    }
}

@Preview(backgroundColor = 0xFF0A0A0F, showSystemUi = true)
@Composable
private fun GeneratePasswordPreview() {
    GeneratePasswordScreen()
}
