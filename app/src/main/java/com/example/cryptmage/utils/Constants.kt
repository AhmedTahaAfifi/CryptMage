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
        const val ENCRYPTED_PASSWORD = "encrypted_password"
        const val IV = "iv"
    }

    object Drive {
        const val GOOGLE_SERVER_CLIENT_ID = "1029183500585-iijjor5bnjf1tbnh6jnkrqpq4a1ntdri.apps.googleusercontent.com"
        const val GOOGLE_DRIVE_URI = "https://www.googleapis.com/auth/drive.appdata"
        const val APP_DATABASE_PATH = "cryptmage_dp"
        const val APP_SALT_PATH = "cryptmage_salt.txt"
        const val DB_SALT = "db_salt"
        const val APP_DATA_FOLDER = "appDataFolder"

        const val BACKUP_FILE_NAME = "cryptmage_backup.dp"
        const val BACKUP_FILE_QUERY = "name = '$BACKUP_FILE_NAME'"
    }
}