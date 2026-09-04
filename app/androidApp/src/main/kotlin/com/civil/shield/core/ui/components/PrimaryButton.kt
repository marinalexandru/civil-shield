package com.civil.shield.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.IconSizeMedium
import com.civil.shield.core.ui.theme.MinTouchTarget
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall

/**
 * Reusable elevated Primary Action Button for CivilShield matching Stitch specifications.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    iconContentDescription: String? = null,
    height: Dp = MinTouchTarget,
    shape: RoundedCornerShape = RoundedCornerShape(SpacingSmall),
    containerColor: Color = Color(CivilShieldTheme.colors.pureWhite),
    contentColor: Color = Color(CivilShieldTheme.colors.primaryContainer),
    disabledContainerColor: Color = Color(CivilShieldTheme.colors.surfaceContainerHighest),
    disabledContentColor: Color = Color(CivilShieldTheme.colors.onSurfaceVariant)
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ),
        contentPadding = PaddingValues(horizontal = SpacingMedium),
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.dp,
                modifier = Modifier.size(IconSizeMedium)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = iconContentDescription,
                        tint = contentColor,
                        modifier = Modifier.size(IconSizeMedium)
                    )
                    Spacer(modifier = Modifier.width(SpacingSmall))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}

@Preview(name = "Primary Button - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun PrimaryButtonPreview() {
    CivilShieldTheme {
        PrimaryButton(
            text = "Autentificare cu ROeID",
            leadingIcon = Icons.Default.Fingerprint,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Primary Button - Loading", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun PrimaryButtonLoadingPreview() {
    CivilShieldTheme {
        PrimaryButton(
            text = "Autentificare cu ROeID",
            leadingIcon = Icons.Default.Fingerprint,
            isLoading = true,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
