package com.civil.shield.features.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.core.ui.components.PrimaryButton
import com.civil.shield.core.ui.components.TricolorFlagDivider
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.core.ui.theme.IconSizeLarge
import com.civil.shield.core.ui.theme.SpacingLarge
import com.civil.shield.core.ui.theme.SpacingMedium
import com.civil.shield.core.ui.theme.SpacingSmall
import com.civil.shield.core.ui.theme.SpacingXLarge
import com.civil.shield.features.main.components.GuestProfileCard
import com.civil.shield.features.main.components.UserProfileCard
import com.civil.shield.features.main.ui.MainScreenState
import com.civil.shield.features.main.ui.MainUiAction
import com.civil.shield.features.main.ui.MainViewModel
import com.civilshield.designsystem.AppStrings
import org.koin.androidx.compose.koinViewModel

/**
 * Stateful wrapper for the MainScreen collecting state from the shared KMP MainViewModel.
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.onAction(MainUiAction.ClearError)
        }
    }

    MainScreenContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

/**
 * Stateless Compose layout for the MainScreen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    state: MainScreenState,
    onAction: (MainUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = CivilShieldTheme.colors

    Scaffold(
        containerColor = Color(appColors.primaryContainer),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AppStrings.MAIN_SCREEN_TITLE.defaultText(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(appColors.onPrimary),
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(MainUiAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(appColors.onPrimary)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(appColors.primaryContainer)
                )
            )
        },
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TricolorFlagDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SpacingLarge)
                )

                val user = state.user
                if (user != null) {
                    UserProfileCard(user = user, modifier = Modifier.fillMaxWidth())
                } else {
                    GuestProfileCard(modifier = Modifier.fillMaxWidth())
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SpacingXLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val buttonText = if (state.user != null) {
                    AppStrings.MAIN_SCREEN_LOGOUT_BUTTON.defaultText()
                } else {
                    AppStrings.MAIN_SCREEN_LOGIN_BUTTON.defaultText()
                }
                val buttonAction = if (state.user != null) {
                    MainUiAction.Logout
                } else {
                    MainUiAction.Login
                }
                PrimaryButton(
                    text = buttonText,
                    onClick = { onAction(buttonAction) },
                    isLoading = state.isLoading
                )
            }
        }
    }
}

@Preview(name = "MainScreen - Authenticated", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun MainScreenAuthenticatedPreview() {
    CivilShieldTheme {
        MainScreenContent(
            state = MainScreenState(
                user = UserProfileDto(
                    userId = "auth0|123456",
                    name = "Alexandru Marin",
                    email = "alexandru@example.com",
                    isEmailVerified = true
                )
            ),
            onAction = {}
        )
    }
}

@Preview(name = "MainScreen - Guest", showBackground = true, backgroundColor = 0xFF0A192F)
@Composable
private fun MainScreenGuestPreview() {
    CivilShieldTheme {
        MainScreenContent(
            state = MainScreenState(user = null),
            onAction = {}
        )
    }
}
