package com.civil.shield.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.civilshield.designsystem.AppColors
import com.civilshield.designsystem.AppShapes
import com.civilshield.designsystem.AppSpacing
import com.civilshield.designsystem.AppTypography
import com.savantarch.design.toColorScheme
import com.savantarch.design.toShapes
import com.savantarch.design.toTypography

val LocalAppColors = staticCompositionLocalOf { AppColors() }
val LocalAppSpacing = staticCompositionLocalOf { AppSpacing() }

object CivilShieldTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val spacing: AppSpacing
        @Composable
        get() = LocalAppSpacing.current
}

@Composable
fun CivilShieldTheme(
    appColors: AppColors = AppColors(),
    appTypography: AppTypography = AppTypography(),
    appShapes: AppShapes = AppShapes(),
    appSpacing: AppSpacing = AppSpacing(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = appColors.toColorScheme(isDark = darkTheme)
    val typography = appTypography.toTypography()
    val shapes = appShapes.toShapes()

    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppSpacing provides appSpacing
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}
