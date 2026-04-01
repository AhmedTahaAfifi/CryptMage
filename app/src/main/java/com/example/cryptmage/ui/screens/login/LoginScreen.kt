package com.example.cryptmage.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.appDescriptionTextColor
import com.example.cryptmage.ui.theme.backgroundColor
import com.example.cryptmage.ui.theme.interFont

@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(backgroundColor)
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.logo_cryptmage),
                    contentDescription = stringResource(R.string.logo_crypt_mage)
                )

                Column {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 25.sp,
                        fontFamily = interFont,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.app_description),
                        fontSize = 15.sp,
                        fontFamily = interFont,
                        color = appDescriptionTextColor
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    backgroundColor = 0xFF0A0A0F,
)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}