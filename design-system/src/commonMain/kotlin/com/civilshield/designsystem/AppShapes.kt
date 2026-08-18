package com.civilshield.designsystem

import com.savantarch.design.CornerFamily
import com.savantarch.design.DsShapes
import com.savantarch.design.ShapeAppearance

data class BaseDsShapes(
    override val extraSmall: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 2.0),
    override val small: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 4.0),
    override val medium: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 8.0),
    override val large: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 12.0),
    override val extraLarge: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 16.0)
) : DsShapes

/**
 * CivilShield AppShapes delegating to kmp-design-system DsShapes.
 */
class AppShapes(
    private val base: DsShapes = BaseDsShapes(),
    val full: ShapeAppearance = ShapeAppearance(CornerFamily.ROUNDED, 9999.0)
) : DsShapes by base
