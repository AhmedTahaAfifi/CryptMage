package com.example.cryptmage.ui.screens.home.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrength.STRONG
import com.example.cryptmage.ui.screens.home.VaultDataUiState
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.VaultDataTextColor
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import com.example.cryptmage.ui.theme.VaultImageContainerColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun VaultCard(vaultData: VaultDataUiState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp)
            .background(DarkBlue, RoundedCornerShape(12.sdp))
            .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(12.sdp))
            .padding(8.sdp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(35.sdp)
                        .background(VaultImageContainerColor, RoundedCornerShape(8.sdp))
                        .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(8.sdp))
                        .padding(5.sdp)
                ) {
                    // TODO Add vault image base on the email source
                }
                Spacer(modifier = Modifier.size(8.sdp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = vaultData.name.orEmpty().ifEmpty { stringResource(R.string.untitled_entry) },
                        color = Color.White,
                        fontSize = 11.ssp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (!vaultData.email.isNullOrEmpty()) vaultData.email
                        else vaultData.phoneNumber.orEmpty(),
                        color = VaultDataTextColor,
                        fontSize = 9.ssp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(VaultImageContainerColor, RoundedCornerShape(12.sdp))
                    .padding(vertical = 5.sdp, horizontal = 10.sdp),
            ) {
                Text(
                    text = stringResource(vaultData.passwordStrengthSlug.labelId),
                    fontSize = 9.ssp,
                    color = vaultData.passwordStrengthSlug.color
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VaultCardPreview() {
    VaultCard(VaultDataUiState(
        id = 1,
        name = "GitHub",
        email = "ahmedTest@gmail.com",
        phoneNumber = "akjdklasjdkl",
        passwordStrengthSlug = STRONG
    ))
}