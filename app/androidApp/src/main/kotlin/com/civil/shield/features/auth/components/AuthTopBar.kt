package com.civil.shield.features.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.IconSizeSmall
import com.civil.shield.core.ui.theme.SpacingHuge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXSmall
import com.civilshield.designsystem.AppStrings

/**
 * Top bar section containing language selection selector with rounded-lg style and outline.
 */
@Composable
fun AuthTopBar(
    selectedLanguage: String,
    availableLanguages: List<String>,
    isDropdownExpanded: Boolean,
    onDropdownToggle: (Boolean) -> Unit,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors
    val containerShape = RoundedCornerShape(SpacingSmall)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SpacingHuge),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Surface(
                color = Color(appColors.surfaceContainer),
                shape = containerShape,
                modifier = Modifier
                    .clip(containerShape)
                    .border(
                        width = 1.dp,
                        color = Color(appColors.outlineVariant),
                        shape = containerShape
                    )
                    .clickable { onDropdownToggle(!isDropdownExpanded) }
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = SpacingMedium,
                        vertical = SpacingSmall
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = AppStrings.AUTH_LANGUAGE_CD.defaultText(),
                        tint = Color(appColors.primary),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(SpacingSmall))
                    Text(
                        text = selectedLanguage,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = Color(appColors.onSurface)
                    )
                    Spacer(modifier = Modifier.width(SpacingXSmall))
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = AppStrings.AUTH_SELECT_LANGUAGE_CD.defaultText(),
                        tint = Color(appColors.onSurfaceVariant),
                        modifier = Modifier.size(IconSizeSmall)
                    )
                }
            }

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { onDropdownToggle(false) },
                modifier = Modifier
                    .background(Color(appColors.surfaceContainerHigh))
                    .border(
                        width = 1.dp,
                        color = Color(appColors.outlineVariant),
                        shape = RoundedCornerShape(SpacingMedium)
                    )
            ) {
                availableLanguages.forEach { language ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = language,
                                color = Color(appColors.onSurface),
                                fontWeight = if (language == selectedLanguage) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Normal
                                }
                            )
                        },
                        onClick = { onLanguageSelected(language) }
                    )
                }
            }
        }
    }
}

@Preview(name = "AuthTopBar - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthTopBarPreview() {
    CivilShieldTheme {
        AuthTopBar(
            selectedLanguage = "RO",
            availableLanguages = listOf("RO", "EN", "HU"),
            isDropdownExpanded = false,
            onDropdownToggle = {},
            onLanguageSelected = {}
        )
    }
}
