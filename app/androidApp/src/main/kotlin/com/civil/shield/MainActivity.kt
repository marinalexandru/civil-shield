package com.civil.shield

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.civil.shield.core.ui.theme.CivilShieldTheme
import com.civil.shield.features.auth.AuthScreen
import com.civil.shield.features.auth.ui.AuthScreenViewModel
import com.civil.shield.features.auth.ui.AuthUiAction

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        handleAuthIntent(intent)

        setContent {
            CivilShieldTheme {
                val state by authViewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current

                LaunchedEffect(state.authorizeUrlToLaunch) {
                    state.authorizeUrlToLaunch?.let { url ->
                        android.util.Log.d("CivilShieldAuth", "Launching Custom Tab URL: $url")
                        val customTabsIntent = CustomTabsIntent.Builder()
                            .setShowTitle(true)
                            .build()
                        customTabsIntent.launchUrl(context, Uri.parse(url))
                        authViewModel.onAction(AuthUiAction.ClearAuthorizeUrl)
                    }
                }

                AuthScreen(viewModel = authViewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleAuthIntent(intent)
    }

    private fun handleAuthIntent(intent: Intent?) {
        val uri = intent?.data ?: return
        android.util.Log.d("CivilShieldAuth", "Received deep link callback URI: $uri")
        val code = uri.getQueryParameter("code")
        val state = uri.getQueryParameter("state")
        val error = uri.getQueryParameter("error")
        val errorDescription = uri.getQueryParameter("error_description")

        if (!error.isNullOrBlank()) {
            android.util.Log.e("CivilShieldAuth", "Auth error: $error - $errorDescription")
            authViewModel.onAction(AuthUiAction.SetError(errorDescription ?: error))
        } else if (!code.isNullOrBlank() && !state.isNullOrBlank()) {
            android.util.Log.d("CivilShieldAuth", "Extracted code: $code, state: $state")
            authViewModel.onAction(AuthUiAction.HandleCallback(code, state))
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    CivilShieldTheme {
        AuthScreen()
    }
}
