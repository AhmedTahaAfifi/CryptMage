package com.example.cryptmage.ui.component.appTextField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.ui.theme.LightPurple
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.PasswordStrengthWeak
import com.example.cryptmage.ui.theme.PrimaryColor
import com.example.cryptmage.ui.theme.TextFieldInputColor
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import com.example.cryptmage.ui.theme.VaultImageContainerColor
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelId: Int,
    placeholderId: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    isRequired: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.sdp)
    ) {
        // label + required star
        Row(horizontalArrangement = Arrangement.spacedBy(3.sdp)) {
            Text(
                text = stringResource(labelId),
                color = LightPurple,
                style = MyAppTypography.labelSmall,
                letterSpacing = 0.1.toInt().ssp
            )
            if (isRequired) {
                Text(
                    text = "*",
                    color = PasswordStrengthWeak,
                    style = MyAppTypography.labelSmall,
                )
            }
        }

        // Input
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(placeholderId),
                    color = appDescriptionTextColor,
                    style = MyAppTypography.labelMedium
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.ssp,
                color = TextFieldInputColor
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = VaultImageContainerColor,
                unfocusedContainerColor = VaultImageContainerColor,
                focusedBorderColor = PrimaryColor.copy(alpha = 0.00f),
                unfocusedBorderColor = Color.White.copy(alpha = 0.07f),
                cursorColor = PrimaryColor,
                focusedTextColor = TextFieldInputColor,
                unfocusedTextColor = LightPurple,
                selectionColors = TextSelectionColors(
                    handleColor = PrimaryColor,
                    backgroundColor = PrimaryColor.copy(alpha = 0.2f)
                )
            ),
            shape = RoundedCornerShape(10.sdp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AppTextFieldPreview() {
    AppTextField(
        value = "",
        onValueChange = {},
        labelId = 0,
        placeholderId = 0,
        isRequired = true,
    )
}