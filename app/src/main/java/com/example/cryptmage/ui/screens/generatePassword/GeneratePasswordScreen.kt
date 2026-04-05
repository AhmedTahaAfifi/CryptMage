package com.example.cryptmage.ui.screens.generatePassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    .padding(16.sdp)
                    .background(DarkBlue, RoundedCornerShape(12.sdp))
                    .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(8.sdp))
                    .padding(10.sdp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "K#9mP$"+"qL2@nXn+w!8vR5&Yt",
                        color = PrimaryColor,
                        fontSize = 13.ssp,
                        fontWeight = FontWeight.Medium
                    )
                    PasswordStrengthIndicator(
                        modifier = Modifier.padding(top = 13.sdp),
                        password = "sd"
                    )
                    Row() {

                    }
                }
            }
        }
    }
}

@Preview(backgroundColor = 0xFF0A0A0F, showSystemUi = true)
@Composable
fun GeneratePasswordPreview() {
    GeneratePasswordScreen()
}