package com.example.cryptmage.ui.component.appButton

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.MyAppTypography
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.sdp),
        colors = ButtonDefaults.buttonColors(containerColor = Accent),
        shape = RoundedCornerShape(10.sdp),
        enabled = enabled
    ) {
        Text(
            modifier = Modifier.padding(vertical = 6.sdp),
            text = text,
            style = MyAppTypography.bodyLarge
        )
    }
}