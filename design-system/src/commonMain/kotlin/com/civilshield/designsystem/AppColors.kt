package com.civilshield.designsystem

import com.savantarch.design.DsColors

data class BaseDsColors(
    override val primary: Long = 0xFFB9C7E4L,
    override val onPrimary: Long = 0xFF233148L,
    override val primaryContainer: Long = 0xFF0A192FL,
    override val onPrimaryContainer: Long = 0xFF74829DL,
    override val inversePrimary: Long = 0xFFB9C7E4L,
    override val secondary: Long = 0xFFC6C6C7L,
    override val onSecondary: Long = 0xFF131315L,
    override val secondaryContainer: Long = 0xFF454747L,
    override val onSecondaryContainer: Long = 0xFFC6C6C7L,
    override val tertiary: Long = 0xFFE7BF99L,
    override val onTertiary: Long = 0xFF281400L,
    override val tertiaryContainer: Long = 0xFF281400L,
    override val onTertiaryContainer: Long = 0xFFE7BF99L,
    override val background: Long = 0xFF131315L,
    override val onBackground: Long = 0xFFE4E2E4L,
    override val surface: Long = 0xFF131315L,
    override val onSurface: Long = 0xFFE4E2E4L,
    override val surfaceVariant: Long = 0xFF1F1F21L,
    override val onSurfaceVariant: Long = 0xFFC5C6CDL,
    override val surfaceTint: Long = 0xFFB9C7E4L,
    override val inverseSurface: Long = 0xFFE4E2E4L,
    override val inverseOnSurface: Long = 0xFF131315L,
    override val error: Long = 0xFFFFB4ABL,
    override val onError: Long = 0xFF690005L,
    override val errorContainer: Long = 0xFF93000AL,
    override val onErrorContainer: Long = 0xFFFFDAD6L,
    override val outline: Long = 0xFF8F9097L,
    override val outlineVariant: Long = 0xFF44474DL,
    override val scrim: Long = 0xFF000000L
) : DsColors

/**
 * CivilShield AppColors delegating to kmp-design-system DsColors.
 */
class AppColors(
    private val base: DsColors = BaseDsColors(),
    val surfaceContainerLowest: Long = 0xFF0E0E10L,
    val surfaceContainerLow: Long = 0xFF1B1B1DL,
    val surfaceContainer: Long = 0xFF1F1F21L,
    val surfaceContainerHigh: Long = 0xFF2A2A2CL,
    val surfaceContainerHighest: Long = 0xFF343536L,
    val surfaceBright: Long = 0xFF39393BL,
    val surfaceDim: Long = 0xFF131315L,
    val primaryFixed: Long = 0xFFD6E3FFL,
    val primaryFixedDim: Long = 0xFFB9C7E4L,
    val emergencySos: Long = 0xFFCE1126L,
    val activeWarning: Long = 0xFFFCD116L
) : DsColors by base
