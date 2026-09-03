package com.civil.shield.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.civil.shield.auth.AuthRepository
import com.civil.shield.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

import com.civil.shield.navigation.AppDestination
import com.civil.shield.navigation.AppNavigator

/**
 * KMP ViewModel managing AuthScreen state and user actions,
 * powered by the official androidx.lifecycle:lifecycle-viewmodel multiplatform library.
 */
@KoinViewModel
open class AuthScreenViewModel(
    private val authRepository: AuthRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    init {
        authRepository.authState
            .onEach { authState ->
                when (authState) {
                    is AuthState.Unauthenticated -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                authenticatedUser = null,
                                errorMessage = null
                            )
                        }
                    }
                    is AuthState.Authenticating -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                    is AuthState.Authenticated -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                authenticatedUser = authState.user,
                                errorMessage = null,
                                authorizeUrlToLaunch = null
                            )
                        }
                    }
                    is AuthState.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = authState.message,
                                authorizeUrlToLaunch = null
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: AuthUiAction) {
        when (action) {
            is AuthUiAction.SelectLanguage -> selectLanguage(action.language)
            is AuthUiAction.SetDropdownExpanded -> setDropdownExpanded(action.expanded)
            is AuthUiAction.Authenticate -> authenticate(action.connection)
            is AuthUiAction.HandleCallback -> handleCallback(action.code, action.state)
            is AuthUiAction.SetError -> setError(action.message)
            AuthUiAction.ClearAuthorizeUrl -> clearAuthorizeUrl()
            AuthUiAction.Logout -> logout()
            AuthUiAction.NavigateToMain -> navigateToMain()
            AuthUiAction.ClickTerms -> Unit
            AuthUiAction.ClickPrivacy -> Unit
        }
    }

    fun navigateToMain() {
        navigator.navigateTo(AppDestination.Main)
    }

    fun setError(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message,
                authorizeUrlToLaunch = null
            )
        }
    }

    fun selectLanguage(language: String) {
        _state.update {
            it.copy(
                selectedLanguage = language,
                isLanguageDropdownExpanded = false
            )
        }
    }

    fun setDropdownExpanded(expanded: Boolean) {
        _state.update {
            it.copy(isLanguageDropdownExpanded = expanded)
        }
    }

    fun authenticate(connection: String? = null) {
        val pkceSession = authRepository.startPkceLogin(connection = connection)
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                authorizeUrlToLaunch = pkceSession.authorizeUrl
            )
        }
    }

    fun clearAuthorizeUrl() {
        _state.update {
            it.copy(authorizeUrlToLaunch = null)
        }
    }

    fun handleCallback(code: String, state: String) {
        viewModelScope.launch {
            authRepository.handleCallback(code, state)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
