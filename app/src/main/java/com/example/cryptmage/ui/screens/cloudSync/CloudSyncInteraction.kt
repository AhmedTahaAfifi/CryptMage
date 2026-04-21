package com.example.cryptmage.ui.screens.cloudSync

import android.content.Context

interface CloudSyncInteraction {

    fun onLinkDriveClick(activityContext: Context)
    fun onSyncClick()
    fun onDrivePermissionGranted(email: String?)
    fun onExportClick(activityContext: Context)
    }