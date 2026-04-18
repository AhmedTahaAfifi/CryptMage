package com.example.cryptmage.data.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cryptmage.R
import com.example.cryptmage.ui.navGraph.AppRoute

enum class BottomNavDestination(val route: AppRoute, val labelId: Int, val icon: ImageVector) {
    Home(AppRoute.Home, R.string.home_title, Icons.Rounded.GridView),
    SYNC(AppRoute.ClaudeSync, R.string.claude_sync_title, Icons.Rounded.Cloud),
}