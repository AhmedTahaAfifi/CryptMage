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
import androidx.compose.ui.unit.sp
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.PasswordStrengthSlug
import com.example.cryptmage.data.enums.PasswordStrengthSlug.*
import com.example.cryptmage.data.moudels.VaultData
import com.example.cryptmage.ui.theme.DarkBlue
import com.example.cryptmage.ui.theme.PasswordStrengthMedium
import com.example.cryptmage.ui.theme.PasswordStrengthStrong
import com.example.cryptmage.ui.theme.PasswordStrengthWeak
import com.example.cryptmage.ui.theme.VaultDataTextColor
import com.example.cryptmage.ui.theme.VaultEntryCardBorderColor
import com.example.cryptmage.ui.theme.VaultImageContainerColor

@Composable
fun PasswordItem(vaultData: VaultData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue, RoundedCornerShape(12.dp))
            .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(12.dp))
            .padding(8.dp),
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
                        .size(28.dp)
                        .background(VaultImageContainerColor, RoundedCornerShape(8.dp))
                        .border(0.5.dp, VaultEntryCardBorderColor, RoundedCornerShape(8.dp))
                        .padding(5.dp)
                ) {
                    // TODO Add vault image base on the email source
                }
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = vaultData.name.orEmpty().ifEmpty { stringResource(R.string.untitled_entry) },
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (!vaultData.email.isNullOrEmpty()) vaultData.email
                        else vaultData.phoneNumber.orEmpty(),
                        color = VaultDataTextColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(VaultImageContainerColor, RoundedCornerShape(12.dp))
                    .padding(vertical = 5.dp, horizontal = 10.dp),
            ) {
                Text(
                    text = vaultData.passwordStrength.orEmpty(),
                    fontSize = 9.sp,
                    color = when(vaultData.passwordStrengthSlug) {
                        WEAK -> PasswordStrengthWeak
                        MEDIUM -> PasswordStrengthMedium
                        STRONG -> PasswordStrengthStrong
                        VERY_STRONG -> PasswordStrengthStrong
                        null -> Color.White
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordItemPreview() {
    PasswordItem(VaultData(
        name = "GitHub",
        email = "ahmedTest@gmail.com",
        passwordStrength = "strong",
        passwordStrengthSlug = STRONG
    ))
}