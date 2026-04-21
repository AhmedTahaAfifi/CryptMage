package com.example.cryptmage.utils

import java.util.regex.Pattern

object Constants {

    object GeneratePassword {
        const val LOWER_CASE = "abcdefghijklmnopqrstuvwxyz"
        const val UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        const val DIGIT = "0123456789"
        const val SYMBOLS_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?/`~"
        const val AMBIGUOUS = "Il1O0o"
    }

    object String {
        val EMAIL_PATTERN: Pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    }

    object Prefs {
        const val DRIVE_USER_EMAIL = "drive_user_email"
    }

}