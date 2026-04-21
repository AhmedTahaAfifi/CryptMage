package com.example.cryptmage.ui.parent

data class AppErrorState(
    val messageId: Int,
    val exception: Throwable? = null
)