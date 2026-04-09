package com.example.cryptmage.ui.screens.generatePassword.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cryptmage.R
import com.example.cryptmage.ui.component.labelToggleRow.LabelToggleRow

@Composable
internal fun GeneratorToggleGroup(
    upperCaseEnabled: Boolean,
    numbersEnabled: Boolean,
    symbolsEnabled: Boolean,
    avoidAmbiguousEnabled: Boolean,
    onUpperCaseToggle: () -> Unit,
    onNumbersToggle: () -> Unit,
    onSymbolsToggle: () -> Unit,
    onAvoidAmbiguousToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        LabelToggleRow(
            label = stringResource(R.string.uppercase),
            isChecked = upperCaseEnabled,
            onCheckedChange = onUpperCaseToggle
        )
        LabelToggleRow(
            label = stringResource(R.string.numbers),
            isChecked = numbersEnabled,
            onCheckedChange = onNumbersToggle
        )
        LabelToggleRow(
            label = stringResource(R.string.symbols),
            isChecked = symbolsEnabled,
            onCheckedChange = onSymbolsToggle
        )
        LabelToggleRow(
            label = stringResource(R.string.avoid_ambiguous),
            isChecked = avoidAmbiguousEnabled,
            onCheckedChange = onAvoidAmbiguousToggle
        )
    }
}
