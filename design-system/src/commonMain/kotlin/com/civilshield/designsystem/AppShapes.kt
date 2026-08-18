package com.civilshield.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * CivilShield Design System Shape Tokens (Soft Geometry).
 */
data class AppShapes(
    val sm: CornerBasedShape = RoundedCornerShape(2.dp),
    val md: CornerBasedShape = RoundedCornerShape(4.dp),
    val lg: CornerBasedShape = RoundedCornerShape(8.dp),
    val xl: CornerBasedShape = RoundedCornerShape(12.dp),
    val full: CornerBasedShape = RoundedCornerShape(9999.dp)
)

val LocalAppShapes = staticCompositionLocalOf { AppShapes() }
