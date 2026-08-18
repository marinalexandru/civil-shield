package com.civilshield.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * CivilShield Design System Color Tokens.
 */
data class AppColors(
    val background: Color = Color(0xFF131315),
    val surface: Color = Color(0xFF131315),
    val surfaceContainerLowest: Color = Color(0xFF0E0E10),
    val surfaceContainerLow: Color = Color(0xFF1B1B1D),
    val surfaceContainer: Color = Color(0xFF1F1F21),
    val surfaceContainerHigh: Color = Color(0xFF2A2A2C),
    val surfaceContainerHighest: Color = Color(0xFF343536),
    val surfaceBright: Color = Color(0xFF39393B),
    val surfaceDim: Color = Color(0xFF131315),
    val onSurface: Color = Color(0xFFE4E2E4),
    val onSurfaceVariant: Color = Color(0xFFC5C6CD),
    val primary: Color = Color(0xFFB9C7E4),
    val onPrimary: Color = Color(0xFF233148),
    val primaryContainer: Color = Color(0xFF0A192F),
    val onPrimaryContainer: Color = Color(0xFF74829D),
    val primaryFixed: Color = Color(0xFFD6E3FF),
    val primaryFixedDim: Color = Color(0xFFB9C7E4),
    val secondary: Color = Color(0xFFC6C6C7),
    val secondaryContainer: Color = Color(0xFF454747),
    val tertiary: Color = Color(0xFFE7BF99),
    val tertiaryContainer: Color = Color(0xFF281400),
    val error: Color = Color(0xFFFFB4AB),
    val errorContainer: Color = Color(0xFF93000A),
    val emergencySos: Color = Color(0xFFCE1126),
    val activeWarning: Color = Color(0xFFFCD116),
    val outline: Color = Color(0xFF8F9097),
    val outlineVariant: Color = Color(0xFF44474D)
)

val LocalAppColors = staticCompositionLocalOf { AppColors() }
