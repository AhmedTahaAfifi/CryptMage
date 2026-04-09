package com.example.cryptmage.uitls.extensions.string

import com.example.cryptmage.uitls.Constants

fun String.isValidEmail(): Boolean {
    val matcher = Constants.String.EMAIL_PATTERN.matcher(this)

    return matcher.matches()
}