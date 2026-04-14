package com.example.cryptmage.ui.component.passwordStrengthIndicatorInline

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrength
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface3
import com.example.cryptmage.ui.theme.Text3
import ir.kaaveh.sdpcompose.sdp

@Composable
fun PasswordStrengthIndicatorInline(
    modifier: Modifier = Modifier,
    password: String
) {
    val strength = remember(password) { PasswordStrength.analyze(password) }
    val color by animateColorAsState(
        targetValue = strength.color,
        animationSpec = tween(400),
        label = "strength_color"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.strength),
            color = Text3,
            style = MyAppTypography.labelSmall,
            letterSpacing = 0.12.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 4.sdp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.sdp)
        ) {
            IndicatorBars(filledBars = strength.bars, color)
        }
    }
}

@Composable
fun IndicatorBars(filledBars: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val isFilled = index < filledBars
            val alpha by animateFloatAsState(
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = index * 50
                ),
                label = "bar_alpha_$index",
                targetValue = if (isFilled) 1f else 0f
            )

            Box(
                modifier = Modifier
                    .width(45.sdp)
                    .height(4.sdp)
                    .clip(RoundedCornerShape(99.sdp))
                    .background(if (isFilled) color.copy(alpha = alpha) else Surface3)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordStrengthIndicatorInlinePreview() {
    PasswordStrengthIndicatorInline(password = "a")
}
