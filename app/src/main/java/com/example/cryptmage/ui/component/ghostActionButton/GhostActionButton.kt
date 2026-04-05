package com.example.cryptmage.ui.component.ghostActionButton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.LightPurple
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GhostActionButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier

            .clip(RoundedCornerShape(6.sdp))
            .background(DarkBlue)
            .border(1.sdp, VaultEntryCardBorderColor, RoundedCornerShape(6.sdp))
            .clickable{ onClick() }
            .padding(horizontal = 20.sdp, vertical = 10.sdp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 15.ssp,
            color = LightPurple,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GhostActionButtonPreview() {
    GhostActionButton(label = "copy") { }
}