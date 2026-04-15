package com.example.cryptmage.ui.component.appOutlineButton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.cryptmage.data.enums.OutlinedButtonVariant
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Surface3
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    variant: OutlinedButtonVariant = OutlinedButtonVariant.DEFAULT,
    enabled: Boolean = true,
    height: Dp = 42.sdp,
    fontSize: TextUnit = 11.ssp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor by animateColorAsState(
        targetValue = if(isPressed) Surface3 else Color.Transparent,
        animationSpec = tween(120),
        label = "btn_bg"
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isPressed)
            variant.borderColor.copy(alpha = (variant.borderColor.alpha * 2f).coerceAtMost(1f))
        else variant.borderColor,
        animationSpec = tween(120),
        label = "btn_border"
    )

    OutlinedButton(
        modifier = modifier.height(height),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(12.sdp),
        border = BorderStroke(
            width = 0.5.toInt().sdp,
            color = if (enabled) animatedBorderColor else variant.borderColor.copy(alpha = 0.3f)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor =  variant.contentColor,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = variant.contentColor.copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(horizontal = 16.sdp, vertical = 0.sdp),
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Normal,
            color = if (enabled) variant.contentColor else variant.contentColor.copy(alpha = 0.3f)
        )
    }
}