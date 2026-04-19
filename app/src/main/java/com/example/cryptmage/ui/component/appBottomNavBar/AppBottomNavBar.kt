package com.example.cryptmage.ui.component.appBottomNavBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.cryptmage.data.enums.BottomNavDestination
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.Surface
import com.example.cryptmage.ui.theme.Text3
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AppBottomNavBar(
    modifier: Modifier = Modifier,
    selected: BottomNavDestination?,
    onItemClick: (BottomNavDestination) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.sdp)
                .background(Border2)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface)
                .padding(top = 6.sdp, bottom = 12.sdp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavDestination.entries.forEach { destination ->
                NavBarItem(
                    destination = destination,
                    selected = selected == destination,
                    onClick = { onItemClick(destination) })
            }
        }
    }
}

@Composable
private fun NavBarItem(
    destination: BottomNavDestination, selected: Boolean, onClick: () -> Unit
) {
    val iconTint by animateColorAsState(
        targetValue = if (selected) Accent else Text3,
        animationSpec = tween(durationMillis = 250),
        label = "nav_icon_tint_${destination.name}"
    )

    val dotSize by animateDpAsState(
        targetValue = if (selected) 4.sdp else 0.sdp,
        animationSpec = tween(durationMillis = 250),
        label = "nav_dot_size_${destination.name}"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.sdp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 24.sdp, vertical = 4.sdp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.sdp)
    ) {
        Icon(
            imageVector = destination.icon,
            contentDescription = stringResource(destination.labelId),
            tint = iconTint,
            modifier = Modifier.size(20.sdp)
        )

        Box(
            modifier = Modifier
                .size(dotSize)
                .clip(CircleShape)
                .background(Accent)
        )
    }
}