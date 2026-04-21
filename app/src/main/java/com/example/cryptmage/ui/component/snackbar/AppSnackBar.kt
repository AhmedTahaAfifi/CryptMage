package com.example.cryptmage.ui.component.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cryptmage.ui.theme.Surface2
import com.example.cryptmage.ui.theme.AccentG
import com.example.cryptmage.ui.theme.AccentR
import com.example.cryptmage.ui.theme.Border2
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AppSnackBar(
    modifier: Modifier = Modifier,
    state: SnackBarState
) {
    AnimatedVisibility(
        visible = state.isVisible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.sdp, end = 16.sdp, bottom = 24.sdp)
    ) {
        val color = when (state.state) {
            SnackBarState.States.Success -> AccentG
            SnackBarState.States.Error -> AccentR
        }

        val icon = when (state.state) {
            SnackBarState.States.Success -> Icons.Default.CheckCircle
            SnackBarState.States.Error -> Icons.Default.Warning
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface2, RoundedCornerShape(12.sdp))
                .border(1.dp, Border2, RoundedCornerShape(12.sdp))
                .padding(12.sdp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.sdp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.sdp)
                )

                if (state.messageId != 0) {
                    Text(
                        text = stringResource(state.messageId),
                        color = Color.White,
                        fontSize = 13.ssp,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (state.message.isNotBlank()) {
                    Text(
                        text = state.message,
                        color = Color.White,
                        fontSize = 13.ssp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
