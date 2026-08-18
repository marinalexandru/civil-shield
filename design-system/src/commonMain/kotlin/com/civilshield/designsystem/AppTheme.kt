package com.civilshield.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

/**
 * CivilShield Design System Theme Provider.
 */
@Composable
fun AppTheme(
    colors: AppColors = AppColors(),
    typography: AppTypography = AppTypography(),
    spacing: AppSpacing = AppSpacing(),
    shapes: AppShapes = AppShapes(),
    content: @Composable () -> Unit
) {
    val materialColorScheme = darkColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        primaryContainer = colors.primaryContainer,
        onPrimaryContainer = colors.onPrimaryContainer,
        secondary = colors.secondary,
        secondaryContainer = colors.secondaryContainer,
        tertiary = colors.tertiary,
        tertiaryContainer = colors.tertiaryContainer,
        background = colors.background,
        onBackground = colors.onSurface,
        surface = colors.surface,
        onSurface = colors.onSurface,
        onSurfaceVariant = colors.onSurfaceVariant,
        surfaceContainer = colors.surfaceContainer,
        surfaceContainerHigh = colors.surfaceContainerHigh,
        surfaceContainerHighest = colors.surfaceContainerHighest,
        surfaceContainerLow = colors.surfaceContainerLow,
        surfaceContainerLowest = colors.surfaceContainerLowest,
        surfaceBright = colors.surfaceBright,
        surfaceDim = colors.surfaceDim,
        error = colors.error,
        errorContainer = colors.errorContainer,
        onError = Color(0xFF690005),
        onErrorContainer = Color(0xFFFFDAD6),
        outline = colors.outline,
        outlineVariant = colors.outlineVariant
    )

    val materialTypography = Typography(
        displayLarge = typography.headlineLg,
        headlineLarge = typography.headlineLgMobile,
        headlineMedium = typography.headlineMd,
        titleMedium = typography.callout,
        bodyLarge = typography.bodyLg,
        bodyMedium = typography.bodyMd,
        labelLarge = typography.labelBold
    )

    val materialShapes = Shapes(
        small = shapes.sm,
        medium = shapes.md,
        large = shapes.lg,
        extraLarge = shapes.xl
    )

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppSpacing provides spacing,
        LocalAppShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = materialTypography,
            shapes = materialShapes,
            content = content
        )
    }
}

/**
 * Accessor object for CivilShield Theme tokens.
 */
object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val spacing: AppSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalAppSpacing.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}
