package com.example.cryptmage.ui.component.passwordStrengthIndicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun PasswordStrengthIndicator(modifier: Modifier = Modifier, password: String,) {
    val strength = remember(password) { PasswordStrength.analyze(password) }
    val color by animateColorAsState(
        targetValue = strength.color,
        animationSpec = tween(durationMillis = 400),
        label = "strength_color"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.sdp))
            .background(DarkBlue)
            .border(
                width = 1.sdp,
                color = color,
                shape = RoundedCornerShape(10.sdp)
            )
            .padding(horizontal = 16.sdp, vertical = 12.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Animated bar chart icon
        StrengthBars(filledBars = strength.bars, color)

        Spacer(Modifier.size(12.sdp))

        // Label + entropy
        Column(
            verticalArrangement = Arrangement.spacedBy(2.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(strength.labelId),
                color = color,
                fontSize = 14.ssp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stringResource(strength.entropyLabelId),
                color = color.copy(alpha = 0.6f),
                fontSize = 12.ssp
            )
        }
    }
}

@Composable
private fun StrengthBars(filledBars: Int, color: Color) {
    val barHeights = listOf(8.sdp, 11.sdp, 15.sdp, 19.sdp, 23.sdp)
    val emptyColor = Color.White.copy(alpha = 0.15f)

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.sdp)
    ) {
        barHeights.forEachIndexed { index, height ->
            val isFilled = index < filledBars

            val animationAlpha by animateFloatAsState(
                targetValue = if (isFilled) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = index * 60
                ),
                label = "bar_alpha_$index"
            )

            Box(
                modifier = Modifier
                    .width(5.sdp)
                    .height(height)
                    .clip(RoundedCornerShape(2.sdp))
                    .background(
                        if (isFilled) color.copy(alpha = animationAlpha)
                        else emptyColor
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PasswordStrengthIndicatorPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFF0A0A0F))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PasswordStrengthIndicator(password = "a")
        PasswordStrengthIndicator(password = "hellos")
        PasswordStrengthIndicator(password = "Hello@12")
        PasswordStrengthIndicator(password = "Hello@123!")
        PasswordStrengthIndicator(password = $$"K#9mP$qL2@nXw!8vR5&Yt")
    }
}