package com.example.cryptmage.utils

import android.content.ClipData
import android.content.ClipboardManager as AndroidClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle

class ClipboardManager(private val context: Context) {

    fun copy(text: String, label: String = "password", isSensitive: Boolean = true) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as AndroidClipboardManager
        val clip = ClipData.newPlainText(label, text)

        if (isSensitive && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            clip.description.extras = PersistableBundle().apply {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
                putBoolean("android.content.extra.SHOW_CONFIRMATION_HINT", false)
            }
        }

        clipboard.setPrimaryClip(clip)
    }

}