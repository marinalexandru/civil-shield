package com.civil.shield.features.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * KMP ViewModel managing AuthScreen state and user actions,
 * powered by the official androidx.lifecycle:lifecycle-viewmodel multiplatform library.
 */
open class AuthScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    fun onAction(action: AuthUiAction) {
        when (action) {
            is AuthUiAction.SelectLanguage -> selectLanguage(action.language)
            is AuthUiAction.SetDropdownExpanded -> setDropdownExpanded(action.expanded)
            AuthUiAction.Authenticate -> authenticate()
            AuthUiAction.ClickTerms -> Unit
            AuthUiAction.ClickPrivacy -> Unit
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

    fun authenticate() {
        // Can launch coroutines in viewModelScope
    }
}
