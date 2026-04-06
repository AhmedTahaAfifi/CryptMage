package com.example.cryptmage.ui.component.passwordLengthSlider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.AccentPurple
import com.example.cryptmage.ui.theme.MyAppTypography
import com.example.cryptmage.ui.theme.PrimaryColor
import com.example.cryptmage.ui.theme.VaultDataTextColor
import com.example.cryptmage.ui.theme.VaultImageContainerColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun PasswordLengthSlider(
    modifier: Modifier = Modifier,
    length: Int,
    onLengthChange: (Int) -> Unit,
    minLength: Int = 8,
    maxLength: Int = 32,
) {
    Column(modifier.fillMaxWidth()) {
        // Label Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.length),
                style = MyAppTypography.labelSmall,
                color = VaultDataTextColor,
            )
            Text(
                text = length.toString(),
                color = AccentPurple
            )
        }

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = length.toFloat(),
            onValueChange = { onLengthChange(it.toInt()) },
            valueRange = minLength.toFloat()..maxLength.toFloat(),
            steps = (maxLength - minLength) - 1,
            colors = SliderDefaults.colors(
                activeTrackColor = PrimaryColor,
                inactiveTrackColor = VaultImageContainerColor,
                thumbColor = Color.White,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PasswordLengthSliderPreview() {
    var length by remember { mutableStateOf(20) }

    Column(
        modifier = Modifier
            .background(Color(0xFF0A0A0F))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PasswordLengthSlider(length = 8, onLengthChange = {})
        PasswordLengthSlider(length = 20,     onLengthChange = {})
        PasswordLengthSlider(length = 32,     onLengthChange = {})
        PasswordLengthSlider(length = length, onLengthChange = { length = it })
    }
}