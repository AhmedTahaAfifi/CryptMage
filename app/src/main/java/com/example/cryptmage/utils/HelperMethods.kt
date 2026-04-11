package com.example.cryptmage.utils

import android.util.Log

object HelperMethods {

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

}