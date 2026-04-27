package com.example.cryptmage.ui.screens.cloudSync

import android.content.IntentSender

sealed class CloudSyncEffect {
    data class RequestDrivePermission(val intentSender: IntentSender) : CloudSyncEffect()
}
