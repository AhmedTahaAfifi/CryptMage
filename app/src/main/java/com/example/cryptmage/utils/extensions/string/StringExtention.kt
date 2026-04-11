package com.example.cryptmage.utils.extensions.string

import com.example.cryptmage.utils.Constants

fun String.isValidEmail(): Boolean {
    val matcher = Constants.String.EMAIL_PATTERN.matcher(this)

    return matcher.matches()
}