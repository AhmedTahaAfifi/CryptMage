package com.example.cryptmage.ui.screens.details.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.DisplayFieldType
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.Accent2
import com.example.cryptmage.ui.theme.AccentG
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Surface3
import com.example.cryptmage.ui.theme.Text2
import com.example.cryptmage.ui.theme.Text3
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DisplayTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String = "",
    fieldType: DisplayFieldType = DisplayFieldType.TEXT,
    trailingTag: String? = null,
    trailingTagColor: Color = AccentG,
    passwordVisibility: Boolean = false,
    isCopied: Boolean = false,
    onCopyIconToggle: () -> Unit = {},
    onShowPasswordToggle: () -> Unit = {},
) {
    val displayValue = when {
        fieldType == DisplayFieldType.PASSWORD && !passwordVisibility -> {
            "•".repeat(value.length)
        }
        else -> value
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.sdp))
            .background(Surface2)
            .border(0.5.toInt().sdp, Border2, RoundedCornerShape(10.sdp))
            .padding(horizontal = 12.sdp, vertical = 8.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.sdp)
        ) {
            Text(
                text = label,
                color = Text3,
                style = MyAppTypography.labelSmall
            )

            Text(
                text = displayValue,
                color = when (fieldType) {
                    DisplayFieldType.PASSWORD -> Accent2
                    DisplayFieldType.DATE -> Text3
                    else -> Text2
                },
                style = when (fieldType) {
                    DisplayFieldType.PASSWORD -> MyAppTypography.labelLarge
                    else -> MyAppTypography.labelSmall
                },
                maxLines = if (fieldType == DisplayFieldType.DATE) 1 else 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(10.sdp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.sdp)
        ) {
            // Trailing Tag
            trailingTag?.let { tag ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(99.sdp))
                        .background(trailingTagColor.copy(alpha = 0.1f))
                        .border(
                            0.5.toInt().sdp,
                            trailingTagColor.copy(alpha = 0.3f),
                            RoundedCornerShape(99.sdp)
                        )
                        .padding(horizontal = 7.sdp, vertical = 3.sdp)
                ) {
                    Text(
                        text = tag,
                        color = trailingTagColor,
                        style = MyAppTypography.labelSmall
                    )
                }
            }

            // Reveal toggle - only for password
            if (fieldType == DisplayFieldType.PASSWORD) {
                ActionIconButton(onClick = onShowPasswordToggle) {
                    Icon(
                        modifier = Modifier.size(13.sdp),
                        imageVector = if (passwordVisibility) Icons.Rounded.VisibilityOff
                        else Icons.Rounded.Visibility,
                        contentDescription = if (passwordVisibility)
                            stringResource(R.string.hide_password)
                        else
                            stringResource(R.string.show_password),
                        tint = if (passwordVisibility) Accent else Text3
                    )
                }
            }

            if (fieldType != DisplayFieldType.DATE) {
                ActionIconButton(onClick = onCopyIconToggle) {
                    AnimatedContent(
                        targetState = isCopied,
                        transitionSpec = {
                            fadeIn(tween(150)) togetherWith fadeOut(tween(150))
                        },
                        label = "copy_icon"
                    ) { isCopied ->
                        Icon(
                            modifier = Modifier.size(13.sdp),
                            imageVector = if (isCopied) Icons.Rounded.Check
                            else Icons.Rounded.ContentCopy,
                            contentDescription = if (isCopied) stringResource(R.string.copied)
                            else stringResource(R.string.copy),
                            tint = if (isCopied) AccentG else Text3
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionIconButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(26.sdp)
            .clip(RoundedCornerShape(7.sdp))
            .background(Surface3)
            .border(0.5.toInt().sdp, Border2, RoundedCornerShape(7.sdp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun DisplayTextFieldPreview() {
    DisplayTextField {}
}