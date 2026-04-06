package com.example.cryptmage.ui.screens.generatePassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.R
import com.example.cryptmage.ui.component.generatedPasswordText.GeneratedPasswordText
import com.example.cryptmage.ui.component.ghostActionButton.GhostActionButton
import com.example.cryptmage.ui.component.passwordLengthSlider.PasswordLengthSlider
import com.example.cryptmage.ui.component.passwordStrengthIndicator.PasswordStrengthIndicator
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.PrimaryColor
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GeneratePasswordScreen(modifier: Modifier = Modifier) {
    var length by remember { mutableIntStateOf(20) }
    val modeOptions = listOf("Random", "Custom")
    var selectedMode by remember { mutableStateOf(modeOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.sdp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.sdp)
                .background(DarkBlue, RoundedCornerShape(12.sdp))
                .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(12.sdp))
                .padding(vertical = 16.sdp, horizontal = 12.sdp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                GeneratedPasswordText("K#9mP\\\$qL2@nXw!8skjdha7whkjdanvR5&Yt")

                PasswordStrengthIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    password = "K#9mP\\\$qL2@nXw!8skjdha7whkjdanvR5&Yt"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.sdp, Alignment.CenterHorizontally)
                ) {
                    GhostActionButton(
                        label = stringResource(R.string.copy)
                    ) { }
                    GhostActionButton(
                        label = stringResource(R.string.refresh)
                    ) { }
                }
            }
        }

        PasswordLengthSlider(
            modifier = Modifier.padding(top = 20.sdp),
            length = length,
            onLengthChange = { length = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* TODO: Save password */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.sdp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryColor
            ),
            shape = RoundedCornerShape(10.sdp)
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 16.ssp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 6.sdp)
            )
        }
    }
}

@Preview(backgroundColor = 0xFF0A0A0F, showSystemUi = true)
@Composable
private fun GeneratePasswordPreview() {
    GeneratePasswordScreen()
}