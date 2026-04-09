package com.example.cryptmage.uitls

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle

object ClipboardUtils {
    fun copyToClipboard(context: Context, text: String, label: String = "password", isSensitive: Boolean = true) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
