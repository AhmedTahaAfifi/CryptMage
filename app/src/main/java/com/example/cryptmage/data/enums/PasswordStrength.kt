package com.example.cryptmage.data.enums

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.cryptmage.R
import com.example.cryptmage.ui.theme.PasswordStrengthMedium
import com.example.cryptmage.ui.theme.PasswordStrengthStrong
import com.example.cryptmage.ui.theme.PasswordStrengthWeak
import kotlin.math.log2

enum class PasswordStrength(
    val labelId: Int,
    val entropyLabelId: Int,
    val bars: Int,
    val minEntropy: Double,
    val color: Color
) {
    WEAK(R.string.weak, R.string.weak_bit_entropy, 1, 0.0, PasswordStrengthWeak),
    MEDIUM(R.string.medium, R.string.medium_bit_entropy, 2, 28.0, PasswordStrengthMedium),
    STRONG(R.string.strong, R.string.strong_bit_entropy, 4, 60.0, PasswordStrengthStrong),
    VERY_STRONG(R.string.very_strong, R.string.very_strong_bit_entropy, 5, 128.0, PasswordStrengthStrong);

    companion object {
        fun analyze(password: String): PasswordStrength {
            val entropy = calculateEntropy(password)
            return entries.lastOrNull { entropy >= it.minEntropy } ?: WEAK
        }

        private fun calculateEntropy(password: String): Double {
            if (password.isEmpty()) return 0.0

            // Calculate pool size based on character classes used
            var poolSize = 0
            if (password.any { it.isLowerCase() }) poolSize += 26
            if (password.any { it.isUpperCase() }) poolSize += 26
            if (password.any { it.isDigit() }) poolSize += 10
            if (password.any { it.isLetterOrDigit() }) poolSize += 32

            return if (poolSize == 0) 0.0
            else password.length * log2(poolSize.toDouble())
        }
    }
}