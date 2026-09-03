package com.civil.shield.features.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.civil.shield.core.ui.components.PrimaryButton
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXLarge
import com.civilshield.designsystem.AppStrings

/**
 * Bottom footer section containing the primary ROeID button, security badge, and legal links.
 */
@Composable
fun AuthFooter(
    isLoading: Boolean,
    isAuthenticated: Boolean = false,
    onAuthenticate: () -> Unit,
    onLogout: () -> Unit = {},
    onShortcutToMain: () -> Unit = {},
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = SpacingXLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isAuthenticated) {
            PrimaryButton(
                text = AppStrings.AUTH_LOGOUT.defaultText(),
                onClick = onLogout,
                isLoading = isLoading
            )
        } else {
            PrimaryButton(
                text = AppStrings.AUTH_LOGIN_BUTTON.defaultText(),
                onClick = onAuthenticate,
                isLoading = isLoading,
                leadingIcon = Icons.Default.Fingerprint,
                iconContentDescription = AppStrings.AUTH_LOGIN_BUTTON.defaultText()
            )
        }

        Spacer(modifier = Modifier.height(SpacingSmall))

        Text(
            text = AppStrings.AUTH_DEV_SHORTCUT_MAIN.defaultText(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            ),
            color = Color(appColors.flagYellow),
            modifier = Modifier
                .clickable { onShortcutToMain() }
                .padding(SpacingSmall)
        )

        Spacer(modifier = Modifier.height(SpacingSmall))

        Text(
            text = AppStrings.AUTH_PROTECTED_BY.defaultText(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            ),
            color = Color(appColors.onSurfaceVariant).copy(alpha = 0.60f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SpacingMedium))

        Row(
            horizontalArrangement = Arrangement.spacedBy(SpacingXLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AppStrings.AUTH_TERMS.defaultText(),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(appColors.onSurfaceVariant),
                modifier = Modifier.clickable { onTermsClick() }
            )
            Text(
                text = AppStrings.AUTH_PRIVACY.defaultText(),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(appColors.onSurfaceVariant),
                modifier = Modifier.clickable { onPrivacyClick() }
            )
        }
    }
}

@Preview(name = "AuthFooter - Unauthenticated", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthFooterUnauthenticatedPreview() {
    CivilShieldTheme {
        AuthFooter(
            isLoading = false,
            isAuthenticated = false,
            onAuthenticate = {},
            onTermsClick = {},
            onPrivacyClick = {}
        )
    }
}

@Preview(name = "AuthFooter - Authenticated", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthFooterAuthenticatedPreview() {
    CivilShieldTheme {
        AuthFooter(
            isLoading = false,
            isAuthenticated = true,
            onAuthenticate = {},
            onLogout = {},
            onTermsClick = {},
            onPrivacyClick = {}
        )
    }
}
