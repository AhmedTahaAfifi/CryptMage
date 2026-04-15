package com.example.cryptmage.utils

import android.content.Context

class ClipboardManager(private val context: Context) {

    fun copy(text: String) {
        ClipboardUtils.copyToClipboard(context, text)
    }

}