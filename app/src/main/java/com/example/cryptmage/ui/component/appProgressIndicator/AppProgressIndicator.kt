package com.example.cryptmage.ui.component.appProgressIndicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cryptmage.ui.theme.Accent

@Composable
fun AppProgressIndicator(isVisible: Boolean = false) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = Accent
        )
    }
}