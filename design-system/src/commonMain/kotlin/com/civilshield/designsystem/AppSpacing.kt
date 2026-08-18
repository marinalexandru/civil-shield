package com.civilshield.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CivilShield Design System Spacing Tokens (8dp base unit grid).
 */
data class AppSpacing(
    val none: Dp = 0.dp,
    val xxs: Dp = 2.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 20.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 56.dp,
    val baseUnit: Dp = 8.dp,
    val touchTargetMin: Dp = 56.dp,
    val containerMargin: Dp = 20.dp,
    val gutter: Dp = 16.dp,
    val ghostBorderWidth: Dp = 2.dp
)

val LocalAppSpacing = staticCompositionLocalOf { AppSpacing() }
