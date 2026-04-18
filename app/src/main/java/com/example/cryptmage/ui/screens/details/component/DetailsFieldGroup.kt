package com.example.cryptmage.ui.screens.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cryptmage.R
import com.example.cryptmage.data.enums.DisplayFieldType
import com.example.cryptmage.ui.theme.AccentG
import com.example.cryptmage.ui.theme.AccentY
import com.example.cryptmage.utils.HelperMethods.isRecent
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DetailsFieldGroup(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    lastUpdated: String = "",
    lastUpdatedTimestamp: Long? = null,
    isPasswordVisible: Boolean = false,
    isPasswordCopied: Boolean = false,
    isEmailCopied: Boolean = false,
    onCopyPasswordToggled: () -> Unit,
    onEmailToggled: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.sdp)
    ) {
        DisplayTextField(
            label = stringResource(R.string.text_field_email),
            value = email,
            fieldType = DisplayFieldType.TEXT,
            onCopyIconClick = onEmailToggled,
            isCopied = isEmailCopied
        )
        DisplayTextField(
            label = stringResource(R.string.label_password),
            value = password,
            fieldType = DisplayFieldType.PASSWORD,
            passwordVisibility = isPasswordVisible,
            isCopied = isPasswordCopied,
            onCopyIconClick = onCopyPasswordToggled,
        )
        DisplayTextField(
            label = stringResource(R.string.label_last_updated),
            value = lastUpdated,
            fieldType = DisplayFieldType.DATE,
            trailingTag = if (isRecent(lastUpdatedTimestamp)) "fresh" else "old",
            trailingTagColor = if (isRecent(lastUpdatedTimestamp)) AccentG else AccentY,
        )
    }
}