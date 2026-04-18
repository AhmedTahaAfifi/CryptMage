package com.example.cryptmage.ui.component.appButton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.MyAppTypography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    height: Dp = 42.sdp,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        colors = ButtonDefaults.buttonColors(containerColor = Accent),
        shape = RoundedCornerShape(10.sdp),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MyAppTypography.bodyLarge
        )
    }
}