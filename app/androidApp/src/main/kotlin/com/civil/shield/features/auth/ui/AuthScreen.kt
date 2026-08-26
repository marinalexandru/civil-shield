package com.civil.shield.features.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.civil.shield.core.ui.components.CivilShieldLogo
import com.civil.shield.core.ui.components.PrimaryButton
import com.civil.shield.core.ui.components.TricolorFlagDivider
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.IconSizeSmall
import com.civil.shield.core.ui.theme.SpacingHuge
import com.civil.shield.core.ui.theme.SpacingLarge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXLarge
import com.civil.shield.core.ui.theme.SpacingXSmall
import com.civil.shield.core.ui.theme.SpacingXXLarge
import com.civil.shield.core.ui.theme.SpacingXXSmall
import com.civilshield.designsystem.AppStrings

/**
 * Stateful entry point for the AuthScreen collecting state from the shared KMP ViewModel.
 */
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AuthScreenContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

/**
 * Stateless presentation of the AuthScreen aligned with Stitch project specifications.
 */
@Composable
fun AuthScreenContent(
    state: AuthScreenState,
    onAction: (AuthUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors

    Scaffold(
        containerColor = Color(appColors.primaryContainer),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = SpacingLarge, vertical = SpacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AuthTopBar(
                selectedLanguage = state.selectedLanguage,
                availableLanguages = state.availableLanguages,
                isDropdownExpanded = state.isLanguageDropdownExpanded,
                onDropdownToggle = { onAction(AuthUiAction.SetDropdownExpanded(it)) },
                onLanguageSelected = { onAction(AuthUiAction.SelectLanguage(it)) }
            )

            AuthIdentityCluster()

            AuthFooter(
                isLoading = state.isLoading,
                onAuthenticate = { onAction(AuthUiAction.Authenticate) },
                onTermsClick = { onAction(AuthUiAction.ClickTerms) },
                onPrivacyClick = { onAction(AuthUiAction.ClickPrivacy) }
            )
        }
    }
}

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

/**
 * Bottom footer section containing the primary ROeID button, security badge, and legal links.
 */
@Composable
fun AuthFooter(
    isLoading: Boolean,
    onAuthenticate: () -> Unit,
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
        PrimaryButton(
            text = AppStrings.AUTH_LOGIN_BUTTON.defaultText(),
            onClick = onAuthenticate,
            isLoading = isLoading,
            leadingIcon = Icons.Default.Fingerprint,
            iconContentDescription = AppStrings.AUTH_LOGIN_BUTTON.defaultText()
        )

        Spacer(modifier = Modifier.height(SpacingMedium))

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

@Preview(showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
fun AuthScreenPreview() {
    CivilShieldTheme {
        AuthScreenContent(
            state = AuthScreenState(),
            onAction = {}
        )
    }
}
