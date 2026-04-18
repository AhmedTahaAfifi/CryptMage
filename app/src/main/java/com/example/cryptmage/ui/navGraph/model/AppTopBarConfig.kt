package com.example.cryptmage.ui.navGraph.model

data class AppTopBarConfig(
    val titleId: Int,
    val iconId: Int? = null,
    val iconContentDescriptionId: Int? = null,
    val onIconClick: () -> Unit = {}
)