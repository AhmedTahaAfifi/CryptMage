package com.example.cryptmage.data.enums

import androidx.compose.ui.graphics.Color
import com.example.cryptmage.ui.theme.Accent
import com.example.cryptmage.ui.theme.AccentR
import com.example.cryptmage.ui.theme.Border
import com.example.cryptmage.ui.theme.Border2
import com.example.cryptmage.ui.theme.Text2
import com.example.cryptmage.ui.theme.Text3

enum class OutlinedButtonVariant(val borderColor: Color, val contentColor: Color) {
    DEFAULT(Border2, Text3),
    ACCENT(Border, Accent),
    DANGER(AccentR.copy(alpha = 0.35f), AccentR),
    EXPORT(Border, Text2)
}