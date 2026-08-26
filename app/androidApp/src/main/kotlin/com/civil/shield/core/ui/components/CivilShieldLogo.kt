package com.civil.shield.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.IconSizeLarge
import com.civil.shield.core.ui.theme.LogoContainerSize
import com.civilshield.designsystem.AppStrings

/**
 * Reusable CivilShield glowing Shield Logo component matching the Stitch design.
 */
@Composable
fun CivilShieldLogo(
    modifier: Modifier = Modifier,
    containerSize: Dp = LogoContainerSize,
    iconSize: Dp = IconSizeLarge,
    icon: ImageVector = Icons.Default.Shield,
    contentDescription: String = AppStrings.AUTH_LOGO_CD.defaultText()
) {
    val appColors = CivilShieldTheme.colors
    val primaryColor = Color(appColors.primary)
    val primaryFixedColor = Color(appColors.primaryFixed)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(containerSize + 24.dp)
            .border(
                width = 1.dp,
                color = primaryColor.copy(alpha = 0.05f),
                shape = CircleShape
            )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(containerSize)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = primaryFixedColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun CivilShieldLogoPreview() {
    CivilShieldTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CivilShieldLogo()
        }
    }
}
