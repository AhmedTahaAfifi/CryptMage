package com.example.cryptmage.ui.screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.Border2
import ir.kaaveh.sdpcompose.sdp

@Composable
fun IconContainer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.sdp)
            .background(Surface2, RoundedCornerShape(8.sdp))
            .border(0.5.dp, Border2, RoundedCornerShape(8.sdp))
            .padding(5.sdp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_shield),
            contentDescription = stringResource(R.string.icon_shield),
            tint = Accent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconContainerPreview() {
    IconContainer()
}