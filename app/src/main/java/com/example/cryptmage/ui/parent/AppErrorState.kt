package com.example.cryptmage.ui.parent

import com.example.cryptmage.domain.exception.AppException

data class AppErrorState(
    val messageId: Int?,
    val exception: AppException? = null
)