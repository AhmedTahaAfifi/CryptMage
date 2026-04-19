package com.example.cryptmage.ui.screens.cloudSync

data class CloudSyncUIState(
    val lastSyncTime: String = "",
    val entryCount: Int = 0,
    val vaultSize: String = "",
    val backupCount: Int = 0,
    val isDriveConnected: Boolean = false,
    val isDropboxConnected: Boolean = false,
    val isOneDriveConnected: Boolean = false,
    val isLoading: Boolean = false,
    val userEmail: String = ""
)
