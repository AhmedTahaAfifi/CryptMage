package com.example.cryptmage.ui.screens.cloudSync.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface3
import com.example.cryptmage.ui.theme.Text1
import com.example.cryptmage.ui.theme.Text2
import ir.kaaveh.sdpcompose.sdp

@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Surface3, RoundedCornerShape(12.sdp))
            .padding(vertical = 12.sdp, horizontal = 8.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MyAppTypography.headlineSmall,
            color = Text1
        )
        Text(
            text = label,
            style = MyAppTypography.labelSmall,
            color = Text2
        )
    }
}
