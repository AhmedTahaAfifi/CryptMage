package com.example.cryptmage.ui.screens.generatePassword

import android.annotation.SuppressLint
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.cryptmage.data.appViewStates.generatePassword.GeneratePasswordViewState
import com.example.cryptmage.data.moudels.VaultData
import com.example.cryptmage.data.moudels.generatePassword.GeneratePasswordData
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
import com.example.cryptmage.uitls.HelperMethods
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import org.koin.androidx.compose.koinViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun GeneratePasswordRoute(
    modifier: Modifier = Modifier,
    viewModel: GeneratePasswordScreenVM = koinViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val navController = AppNavController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            HelperMethods.createLog("showSnackbar")
            if (it == 0) return@collect
            snackbarHostState.showSnackbar(context.getString(it))
        }
    }

    GeneratePasswordScreen(
        modifier = modifier,
        viewState = viewState,
        onLengthChange = viewModel::updateLength,
        onVaultEntryChange = viewModel::updateVaultEntryData,
        onToggleUpperCase = viewModel::toggleUpperCase,
        onToggleNumbers = viewModel::toggleNumbers,
        onToggleSymbols = viewModel::toggleSymbols,
        onToggleAvoidAmbiguous = viewModel::toggleAvoidAmbiguous,
        onRefresh = viewModel::refreshPassword,
        onCopy = {
            viewModel.copyPassword(context, viewState.data.password)
        },
        onSave = {
            viewModel.savePassword(navController)
        }
    )
}

@Composable
private fun GeneratePasswordScreen(
    modifier: Modifier = Modifier,
    viewState: GeneratePasswordViewState,
    onLengthChange: (Int) -> Unit,
    onVaultEntryChange: (VaultData) -> Unit,
    onToggleUpperCase: () -> Unit,
    onToggleNumbers: () -> Unit,
    onToggleSymbols: () -> Unit,
    onToggleAvoidAmbiguous: () -> Unit,
    onRefresh: () -> Unit,
    onCopy: () -> Unit,
    onSave: () -> Unit
) {
    val data = viewState.data

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.sdp)
            .verticalScroll(rememberScrollState()),
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
                GeneratedPasswordText(data.password)

                PasswordStrengthIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    password = data.password
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.sdp, Alignment.CenterHorizontally)
                ) {
                    GhostActionButton(label = stringResource(R.string.copy), onClick = onCopy)
                    GhostActionButton(label = stringResource(R.string.refresh), onClick = onRefresh)
                }
            }
        }

        PasswordLengthSlider(
            modifier = Modifier.padding(top = 20.sdp),
            length = data.length,
            onLengthChange = onLengthChange
        )

        GeneratorToggleGroup(
            upperCaseEnabled = data.upperCase,
            numbersEnabled = data.numbers,
            symbolsEnabled = data.symbols,
            avoidAmbiguousEnabled = data.avoidAmbiguous,
            onUpperCaseEnabledChange = { onToggleUpperCase() },
            onNumbersEnabledChange = { onToggleNumbers() },
            onSymbolsEnabledChange = { onToggleSymbols() },
            onAvoidAmbiguousEnabledChange = { onToggleAvoidAmbiguous() }
        )

        ExpandableDetailsSection(
            vaultEntryData = data.vaultEntryData,
            onDataChange = onVaultEntryChange
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSave,
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
    onUpperCaseEnabledChange: (Boolean) -> Unit,
    onNumbersEnabledChange: (Boolean) -> Unit,
    onSymbolsEnabledChange: (Boolean) -> Unit,
    onAvoidAmbiguousEnabledChange: (Boolean) -> Unit,
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
    vaultEntryData: VaultData?,
    onDataChange: (VaultData) -> Unit
) {
    val data = vaultEntryData ?: VaultData()

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
            value = data.name ?: "",
            onValueChange = { onDataChange(data.copy(name = it)) },
            labelId = R.string.vault_name,
            placeholderId = R.string.vault_name_place_holder,
            isRequired = true
        )

        // Email field
        AppTextField(
            value = data.email ?: "",
            onValueChange = { onDataChange(data.copy(email = it)) },
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
    GeneratePasswordScreen(
        viewState = GeneratePasswordViewState(
            data = GeneratePasswordData(password = $$"K#9mP$qL2@nXw!8", vaultEntryData = VaultData())
        ),
        onLengthChange = {},
        onToggleUpperCase = {},
        onToggleNumbers = {},
        onToggleSymbols = {},
        onToggleAvoidAmbiguous = {},
        onVaultEntryChange = {},
        onRefresh = {},
        onCopy = {},
        onSave = {}
    )
}