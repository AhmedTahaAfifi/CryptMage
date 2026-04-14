package com.example.cryptmage.ui.component.appTextField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.ui.theme.Text2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.AccentR
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.Text1
import com.example.cryptmage.ui.theme.Surface3
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
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorId: Int? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.sdp)
    ) {
        // label + required star
        Text(
            text = stringResource(labelId) + if (isRequired) "*" else "",
            color = if (isError) AccentR else Text2,
            style = MyAppTypography.labelSmall,
        )

        // Input
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
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
                color = Text1
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface3,
                unfocusedContainerColor = Surface3,
                focusedBorderColor = Accent.copy(alpha = 0.00f),
                unfocusedBorderColor = Color.White.copy(alpha = 0.07f),
                cursorColor = Accent,
                focusedTextColor = Text1,
                unfocusedTextColor = Text2,
                selectionColors = TextSelectionColors(
                    handleColor = Accent,
                    backgroundColor = Accent.copy(alpha = 0.2f)
                )
            ),
            shape = RoundedCornerShape(10.sdp)
        )

        if (isError && errorId != null) {
            Text(
                modifier = Modifier.padding(start = 4.sdp),
                text = stringResource(errorId),
                color = AccentR,
                style = MyAppTypography.labelSmall,
            )
        }
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