package com.example.cryptmage.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HelperMethods {

    fun formatDate(timestamp: Long?): String {
        if (timestamp == null) return "Never"
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun createLog(text: String, isError: Boolean = true) {
        val tag = "LOG"
        if (text.length > 4000) {
            if (isError) {
                Log.e(tag, text.substring(0, 4000))
            } else {
                Log.d(tag, text.substring(0, 4000))
            }
            createLog(text.substring(4000), isError)
        } else {
            if (isError) {
                Log.e(tag, text)
            } else {
                Log.d(tag, text)
            }
        }
    }

    fun isRecent(lastUpdatedTimestamp: Long?): Boolean {
        if (lastUpdatedTimestamp == null) return false

        val onDayInMillis = 24 * 60 * 60 * 1000L
        val diff = System.currentTimeMillis() - lastUpdatedTimestamp

        return diff < (30 * onDayInMillis)
    }

}