package com.example.cryptmage.ui.screens.cloudSync.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.AccentG
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Text1
import com.example.cryptmage.ui.theme.Text3
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ProviderCard(
    name: String,
    iconId: Int,
    status: String,
    isConnected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(90.sdp)
            .border(
                width = 0.5.dp,
                color = if (isConnected) Accent else Border2,
                shape = RoundedCornerShape(12.sdp)
            )
            .background(Surface2, RoundedCornerShape(12.sdp))
            .clickable(onClick = onClick)
            .padding(8.sdp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = name,
            modifier = Modifier.size(24.sdp)
        )
        Spacer(modifier = Modifier.height(8.sdp))
        Text(
            text = name,
            color = Text1,
            style = MyAppTypography.labelMedium
        )
        Text(
            text = status,
            color = if (isConnected) AccentG else Text3,
            style = MyAppTypography.labelSmall
        )
    }
}
