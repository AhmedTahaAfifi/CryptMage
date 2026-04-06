package com.example.cryptmage.ui.screens.generatePassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp)
                    .background(DarkBlue, RoundedCornerShape(12.sdp))
                    .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(8.sdp))
                    .padding(10.sdp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.sdp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GeneratedPasswordText("K#9mP\\\$qL2@nXw!8skjdha7whkjdanvR5&Yt")
                    PasswordStrengthIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 13.sdp),
                        password = "K#9mP\\\$qL2@nXw!8skjdha7whkjdanvR5&Yt"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.sdp),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
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
                modifier = Modifier.padding(top = 10.sdp, start = 16.sdp, end = 16.sdp),
                length = 20,
                onLengthChange = {}
            )
        }
    }
}

@Preview(backgroundColor = 0xFF0A0A0F, showSystemUi = true)
@Composable
private fun GeneratePasswordPreview() {
    GeneratePasswordScreen()
}