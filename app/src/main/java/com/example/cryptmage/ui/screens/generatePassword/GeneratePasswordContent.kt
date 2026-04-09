package com.example.cryptmage.ui.screens.generatePassword

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptmage.R
import com.example.cryptmage.ui.component.appTextField.AppTextField
import com.example.cryptmage.ui.component.generatedPasswordText.GeneratedPasswordText
import com.example.cryptmage.ui.component.ghostActionButton.GhostActionButton
import com.example.cryptmage.ui.component.labelToggleRow.LabelToggleRow
import com.example.cryptmage.ui.component.passwordLengthSlider.PasswordLengthSlider
import com.example.cryptmage.ui.component.passwordStrengthIndicator.PasswordStrengthIndicator
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.PrimaryColor
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@Composable
fun GeneratePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: GeneratePasswordViewModel = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current

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
                .background(DarkBlue, RoundedCornerShape(12.sdp))
                .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(12.sdp))
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
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("password", viewState.password)

                        // Mark as sensitive to hide content and minimize system UI on Android 13+
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            clip.description.extras = android.os.PersistableBundle().apply {
                                putBoolean("android.content.extra.IS_SENSITIVE", true)
                                putBoolean("android.content.extra.SHOW_CONFIRMATION_HINT", false)
                            }
                        }

                        clipboard.setPrimaryClip(clip)
                        interaction.onCopy()
                    })
                    GhostActionButton(label = stringResource(R.string.refresh), onClick = interaction::onRefresh)
                }
            }
        }

        PasswordLengthSlider(
            modifier = Modifier.padding(top = 20.sdp),
            length = viewState.length,
            onLengthChange = interaction::onLengthChang
        )

        GeneratorToggleGroup(
            upperCaseEnabled = viewState.upperCase,
            numbersEnabled = viewState.numbers,
            symbolsEnabled = viewState.symbols,
            avoidAmbiguousEnabled = viewState.avoidAmbiguous,
            onUpperCaseEnabledChange = interaction::onToggleUpperCase,
            onNumbersEnabledChange = interaction::onToggleNumbers,
            onSymbolsEnabledChange = interaction::onToggleSymbols,
            onAvoidAmbiguousEnabledChange = interaction::onToggleAvoidAmbiguous
        )

        ExpandableDetailsSection(
            vaultName = viewState.vaultName,
            email = viewState.email,
            onVaultNameChange = interaction::onVaultNameChange,
            onEmailChange = interaction::onEmailChange
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = interaction::onSave,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.sdp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            shape = RoundedCornerShape(10.sdp)
        ) {
            Text(
                text = stringResource(R.string.save_to_vault),
                fontSize = 16.ssp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 6.sdp)
            )
        }
    }
}

@Composable
private fun GeneratorToggleGroup(
    modifier: Modifier = Modifier,
    upperCaseEnabled: Boolean,
    numbersEnabled: Boolean,
    symbolsEnabled: Boolean,
    avoidAmbiguousEnabled: Boolean,
    onUpperCaseEnabledChange: () -> Unit,
    onNumbersEnabledChange: () -> Unit,
    onSymbolsEnabledChange: () -> Unit,
    onAvoidAmbiguousEnabledChange: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LabelToggleRow(
            label = stringResource(R.string.uppercase),
            isChecked = upperCaseEnabled,
            onCheckedChange = onUpperCaseEnabledChange
        )
        LabelToggleRow(
            label = stringResource(R.string.numbers),
            isChecked = numbersEnabled,
            onCheckedChange = onNumbersEnabledChange
        )
        LabelToggleRow(
            label = stringResource(R.string.symbols),
            isChecked = symbolsEnabled,
            onCheckedChange = onSymbolsEnabledChange
        )
        LabelToggleRow(
            label = stringResource(R.string.avoid_ambiguous),
            isChecked = avoidAmbiguousEnabled,
            onCheckedChange = onAvoidAmbiguousEnabledChange
        )
    }
}

@Composable
fun ExpandableDetailsSection(
    vaultName: String,
    email: String,
    onVaultNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.sdp)) {
        // Section label
        Text(
            text = stringResource(R.string.save_as),
            color = appDescriptionTextColor,
            style = MyAppTypography.labelSmall,
            letterSpacing = 0.12.toInt().ssp
        )

        // Name field
        AppTextField(
            value = vaultName,
            onValueChange = onVaultNameChange,
            labelId = R.string.vault_name,
            placeholderId = R.string.vault_name_place_holder,
            isRequired = true
        )

        // Email field
        AppTextField(
            value = email,
            onValueChange = onEmailChange,
            labelId = R.string.text_field_email,
            placeholderId = R.string.email_place_holder,
            keyboardType = KeyboardType.Email,
            isRequired = true
        )
    }
}

@Preview(backgroundColor = 0xFF0A0A0F, showSystemUi = true)
@Composable
private fun GeneratePasswordPreview() {
    /*GeneratePasswordScreen(
        viewState = GeneratePasswordUIState(
            password = "K#9mPqL2@nXw!8"
        ),
        onLengthChange = {},
        onToggleUpperCase = {},
        onToggleNumbers = {},
        onToggleSymbols = {},
        onToggleAvoidAmbiguous = {},
        onVaultNameChange = {},
        onEmailChange = {},
        onRefresh = {},
        onCopy = {},
        onSave = {}
    )*/
}
