package com.civil.shield.features.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.civil.shield.core.ui.components.CivilShieldLogo
import com.civil.shield.core.ui.components.TricolorFlagDivider
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXSmall
import com.civil.shield.core.ui.theme.SpacingXXLarge
import com.civil.shield.core.ui.theme.SpacingXXSmall
import com.civilshield.designsystem.AppStrings

/**
 * Central identity cluster containing the logo, app title, Romanian flag bar, and subtitle.
 */
@Composable
fun AuthIdentityCluster(
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CivilShieldLogo()

        Spacer(modifier = Modifier.height(SpacingXXLarge))

        Text(
            text = AppStrings.AUTH_TITLE.defaultText(),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                lineHeight = 34.sp,
                letterSpacing = 6.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color(appColors.pureWhite),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(SpacingXSmall))

        TricolorFlagDivider(width = 240.dp, height = SpacingXXSmall)

        Spacer(modifier = Modifier.height(SpacingMedium))

        Text(
            text = AppStrings.AUTH_SUBTITLE.defaultText(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            color = Color(appColors.onSurfaceVariant),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .widthIn(max = 280.dp)
                .padding(horizontal = SpacingSmall)
        )
    }
}

@Preview(name = "AuthIdentityCluster - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthIdentityClusterPreview() {
    CivilShieldTheme {
        AuthIdentityCluster()
    }
}
