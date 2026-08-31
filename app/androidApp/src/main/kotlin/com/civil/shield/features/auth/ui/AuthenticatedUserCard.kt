package com.civil.shield.features.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.core.ui.components.CivilShieldLogo
import com.civil.shield.core.ui.components.TricolorFlagDivider
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingLarge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXSmall
import com.civil.shield.core.ui.theme.SpacingXXSmall
import com.civilshield.designsystem.AppStrings

/**
 * Displayed when user is successfully authenticated.
 */
@Composable
fun AuthenticatedUserCard(
    user: UserProfileDto,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors
    val cardShape = RoundedCornerShape(SpacingMedium)

    Surface(
        color = Color(appColors.surfaceContainer),
        shape = cardShape,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(appColors.outlineVariant)),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SpacingSmall)
    ) {
        Column(
            modifier = Modifier.padding(SpacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CivilShieldLogo()

            Spacer(modifier = Modifier.height(SpacingMedium))

            Text(
                text = user.name ?: user.email ?: user.userId,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(appColors.pureWhite),
                textAlign = TextAlign.Center
            )

            if (!user.email.isNullOrBlank() && user.name != null) {
                Spacer(modifier = Modifier.height(SpacingXXSmall))
                Text(
                    text = user.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(appColors.onSurfaceVariant),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(SpacingSmall))

            TricolorFlagDivider(width = 180.dp, height = SpacingXXSmall)

            Spacer(modifier = Modifier.height(SpacingMedium))

            Surface(
                color = Color(appColors.success).copy(alpha = 0.2f),
                shape = RoundedCornerShape(SpacingXSmall),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(appColors.success))
            ) {
                Text(
                    text = AppStrings.AUTH_SUCCESS_BADGE.defaultText(),
                    color = Color(appColors.success),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = SpacingSmall, vertical = SpacingXXSmall)
                )
            }
        }
    }
}

@Preview(name = "AuthenticatedUserCard - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthenticatedUserCardPreview() {
    CivilShieldTheme {
        AuthenticatedUserCard(
            user = UserProfileDto(
                userId = "auth0|123456",
                name = "Alexandru Marin",
                email = "alexandru@example.com",
                isEmailVerified = true
            ),
            onLogout = {}
        )
    }
}
