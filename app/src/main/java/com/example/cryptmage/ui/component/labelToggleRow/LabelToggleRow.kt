package com.example.cryptmage.ui.component.labelToggleRow

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.Text2
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.Surface3
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LabelToggleRow(
    modifier: Modifier = Modifier,
    label: String,
    showDivider: Boolean = true,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCheckedChange() }
                .padding(vertical = 10.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Label
            Text(
                text = label,
                color = Text2,
                style = MyAppTypography.labelSmall
            )

            // Toggle Row
            ToggleRow(
                isChecked = isChecked,
                onCheckedChange = { onCheckedChange() }
            )
        }

        // Divider between rows
        if (showDivider) {
            HorizontalDivider(
                thickness = 0.5.toInt().sdp,
                color = Border2
            )
        }
    }
}

@Composable
private fun ToggleRow(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val trackColor by animateColorAsState(
        targetValue = if (isChecked) Accent else Surface3,
        animationSpec = tween(durationMillis = 250),
        label = stringResource(R.string.track_color)
    )

    val trackBorderColor by animateColorAsState(
        targetValue = if (isChecked) Color.Transparent else Border2,
        animationSpec = tween(durationMillis = 250),
        label = stringResource(R.string.track_border)
    )

    val thumbOffset by animateDpAsState(
        targetValue = if (isChecked) 14.sdp else 2.sdp,
        animationSpec = tween(durationMillis = 250),
        label = "thumb_offset"
    )

    Box(
        modifier = modifier
            .width(28.sdp)
            .height(16.sdp)
            .clip(RoundedCornerShape(99.sdp))
            .background(trackColor)
            .border(0.5.toInt().sdp, trackBorderColor, RoundedCornerShape(99.sdp))
            .clickable { onCheckedChange(!isChecked) }
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset, y = 2.sdp)
                .size(12.sdp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelToggleRowPreview() {
    LabelToggleRow(label = "Show password", isChecked = true, onCheckedChange = {  })
}