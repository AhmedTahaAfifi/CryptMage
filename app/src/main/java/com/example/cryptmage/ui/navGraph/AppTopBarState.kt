package com.example.cryptmage.ui.navGraph

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.example.cryptmage.ui.navGraph.model.AppTopBarConfig

val LocalTopBarConfig = compositionLocalOf {
    mutableStateOf<AppTopBarConfig?>(null)
}