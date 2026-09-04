package com.civil.shield.features.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.SpacingLarge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.features.auth.components.AuthFooter
import com.civil.shield.features.auth.components.AuthIdentityCluster
import com.civil.shield.features.auth.components.AuthTopBar
import com.civil.shield.features.auth.ui.AuthScreenState
import com.civil.shield.features.auth.ui.AuthScreenViewModel
import com.civil.shield.features.auth.ui.AuthUiAction
import org.koin.androidx.compose.koinViewModel

/**
 * Stateful entry point for the AuthScreen collecting state from the shared KMP ViewModel injected via Koin.
 */
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.onAction(AuthUiAction.ClearError)
        }
    }

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
                onAuthenticate = { onAction(AuthUiAction.Authenticate()) },
                onShortcutToMain = { onAction(AuthUiAction.NavigateToMain) },
                onTermsClick = { onAction(AuthUiAction.ClickTerms) },
                onPrivacyClick = { onAction(AuthUiAction.ClickPrivacy) }
            )
        }
    }
}

@Preview(name = "AuthScreen - Default", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthScreenPreview() {
    CivilShieldTheme {
        AuthScreenContent(
            state = AuthScreenState(),
            onAction = {}
        )
    }
}

@Preview(name = "AuthScreen - Loading", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun AuthScreenLoadingPreview() {
    CivilShieldTheme {
        AuthScreenContent(
            state = AuthScreenState(isLoading = true),
            onAction = {}
        )
    }
}
