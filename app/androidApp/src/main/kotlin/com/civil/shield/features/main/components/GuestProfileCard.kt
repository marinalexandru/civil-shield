package com.civil.shield.features.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.civil.shield.core.ui.components.CivilShieldLogo
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingLarge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civilshield.designsystem.AppStrings

/**
 * Displays profile information for an unauthenticated / guest user on the MainScreen.
 */
@Composable
fun GuestProfileCard(
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors

    Column(
        modifier = modifier
            .padding(SpacingLarge)
            .fillMaxWidth()
            .padding(horizontal = SpacingSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CivilShieldLogo()

        Spacer(modifier = Modifier.height(SpacingMedium))

        Text(
            text = AppStrings.GUEST_PROFILE_TITLE.defaultText(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(appColors.pureWhite),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SpacingSmall))

        Text(
            text = AppStrings.GUEST_PROFILE_SUBTITLE.defaultText(),
            style = MaterialTheme.typography.bodySmall,
            color = Color(appColors.onSurfaceVariant),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SpacingSmall))
    }
}

@Preview(name = "GuestProfileCard - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun GuestProfileCardPreview() {
    CivilShieldTheme {
        GuestProfileCard()
    }
}
