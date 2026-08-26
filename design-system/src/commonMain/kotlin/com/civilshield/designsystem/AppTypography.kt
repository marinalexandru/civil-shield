package com.civilshield.designsystem

import com.savantarch.design.DsTypography
import com.savantarch.design.FontSpec

data class BaseDsTypography(
    override val bodyLarge: FontSpec = FontSpec(fontSize = 18.0, lineHeight = 28.0),
    override val bodyMedium: FontSpec = FontSpec(fontSize = 16.0, lineHeight = 24.0),
    override val bodySmall: FontSpec = FontSpec(fontSize = 14.0, lineHeight = 20.0),
    override val displayLarge: FontSpec = FontSpec(fontSize = 32.0, lineHeight = 40.0),
    override val displayMedium: FontSpec = FontSpec(fontSize = 28.0, lineHeight = 34.0),
    override val displaySmall: FontSpec = FontSpec(fontSize = 24.0, lineHeight = 32.0),
    override val headlineLarge: FontSpec = FontSpec(fontSize = 32.0, lineHeight = 40.0),
    override val headlineMedium: FontSpec = FontSpec(fontSize = 24.0, lineHeight = 32.0),
    override val headlineSmall: FontSpec = FontSpec(fontSize = 20.0, lineHeight = 26.0),
    override val labelLarge: FontSpec = FontSpec(fontSize = 14.0, lineHeight = 20.0),
    override val labelMedium: FontSpec = FontSpec(fontSize = 12.0, lineHeight = 16.0),
    override val labelSmall: FontSpec = FontSpec(fontSize = 11.0, lineHeight = 16.0),
    override val titleLarge: FontSpec = FontSpec(fontSize = 22.0, lineHeight = 28.0),
    override val titleMedium: FontSpec = FontSpec(fontSize = 20.0, lineHeight = 26.0),
    override val titleSmall: FontSpec = FontSpec(fontSize = 14.0, lineHeight = 20.0)
) : DsTypography

/**
 * CivilShield AppTypography delegating to kmp-design-system DsTypography.
 */
class AppTypography(
    private val base: DsTypography = BaseDsTypography(),
    val headlineLgMobile: FontSpec = FontSpec(fontSize = 28.0, lineHeight = 34.0),
    val callout: FontSpec = FontSpec(fontSize = 20.0, lineHeight = 26.0),
    val labelBold: FontSpec = FontSpec(fontSize = 14.0, lineHeight = 20.0)
) : DsTypography by base
