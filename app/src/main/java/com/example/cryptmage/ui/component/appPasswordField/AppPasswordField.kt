package com.example.cryptmage.ui.component.appPasswordField

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.Accent2
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.AccentR
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AppPasswordField(
    modifier: Modifier = Modifier,
    value: String,
    visible: Boolean,
    label: String,
    placeHolder: String,
    isError: Boolean = false,
    errorId: Int? = null,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = when {
            isError -> AccentR.copy(alpha = 0.6f)
            value.isNotEmpty() -> Accent.copy(alpha = 0.55f)
            else -> Accent.copy(alpha = 0.18f)
        },
        animationSpec = tween(250),
        label = "border_color"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.sdp)
    ) {
        // Label
        Text(
            text = label,
            color = if (isError) AccentR else appDescriptionTextColor,
            style = MyAppTypography.labelSmall
        )

        // Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = appDescriptionTextColor,
                    style = MyAppTypography.labelSmall
                )
            },
            singleLine = true,
            isError = isError,
            visualTransformation = if (visible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = onClick) {
                    Icon(
                        modifier = Modifier.size(16.sdp),
                        imageVector = if (visible)
                            Icons.Filled.VisibilityOff
                        else
                            Icons.Rounded.Visibility,
                        contentDescription = if (visible)
                            stringResource(R.string.hide_password)
                        else
                            stringResource(R.string.show_password),
                        tint = if (isError)
                            AccentR
                        else
                            appDescriptionTextColor,
                    )
                }
            },
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 13.ssp,
                color = if (isError) AccentR else Accent2,
                letterSpacing = 0.05.toInt().ssp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface2,
                unfocusedContainerColor = Surface2,
                errorContainerColor       = AccentR.copy(alpha = 0.06f),
                focusedBorderColor        = Accent.copy(alpha = 0.55f),
                unfocusedBorderColor      = borderColor,
                errorBorderColor          = AccentR.copy(alpha = 0.6f),
                cursorColor               = Accent,
                errorCursorColor          = AccentR,
                focusedTextColor          = Accent2,
                unfocusedTextColor        = Accent2,
                errorTextColor            = AccentR,
                selectionColors = TextSelectionColors(
                    handleColor = Accent,
                    backgroundColor = Accent.copy(alpha = 0.55f)
                )
            ),
            shape = RoundedCornerShape(10.sdp),
        )

        // Error Message Display
        if (isError && errorId != null) {
            Text(
                text = stringResource(errorId),
                color = AccentR,
                style = MyAppTypography.labelSmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPasswordFieldPreview() {
    //AppPasswordField()
}