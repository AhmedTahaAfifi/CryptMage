package com.example.cryptmage.ui.component.generatedPasswordText

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.ui.theme.PrimaryColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GeneratedPasswordText(
    password: String,
    showCursor: Boolean = true
) {
    val cursorAlpha by rememberInfiniteTransition(label = "cursor_alpha")
        .animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(530, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "cursor_alpha"
        )

    val annotatedText = buildAnnotatedString {
        // Password characters
        withStyle(SpanStyle(
            color = PrimaryColor,
            fontSize = 16.ssp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.ssp
        )) { append(password) }

        if (showCursor) {
            withStyle(SpanStyle(
                color = PrimaryColor.copy(alpha = cursorAlpha),
                fontSize = 16.ssp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace
            )) { append("|") }
        }
    }

    Text(
        text = annotatedText,
        style = TextStyle(lineHeight = 22.ssp),
        overflow = TextOverflow.Visible,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun GeneratedPasswordTextPreview() {
    Column(
        modifier = Modifier
            .background(Color(0xFF0A0A0F))
            .padding(16.sdp),
        verticalArrangement = Arrangement.spacedBy(12.sdp)
    ) {
        // Short password
        GeneratedPasswordText(password = "Hello@123!")

        // Long password — wraps to 2 lines like the Figma design
        GeneratedPasswordText(password = "K#9mP\$qL2@nXw!8vR5&Yt")

        // No cursor (read-only state)
        GeneratedPasswordText(password = "K#9mP\\\$qL2@nXw!8vR5&Yt", showCursor = false)
    }
}