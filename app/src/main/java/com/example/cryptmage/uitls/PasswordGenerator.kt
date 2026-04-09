package com.example.cryptmage.uitls

import kotlin.random.Random

object PasswordGenerator {
    fun generate(
        length: Int,
        includeUpperCase: Boolean,
        includeNumbers: Boolean,
        includeSymbols: Boolean,
        avoidAmbiguous: Boolean
    ): String {
        var charPool = Constants.GeneratePassword.LOWER_CASE
        if (includeUpperCase) charPool += Constants.GeneratePassword.UPPER_CASE
        if (includeNumbers) charPool += Constants.GeneratePassword.DIGIT
        if (includeSymbols) charPool += Constants.GeneratePassword.SYMBOLS_CHARS

        if (avoidAmbiguous) {
            charPool = charPool.filter { it !in Constants.GeneratePassword.AMBIGUOUS }
        }

        if (charPool.isEmpty()) return ""

        return (1..length)
            .map { charPool[Random.nextInt(charPool.length)] }
            .joinToString("")
    }
}
