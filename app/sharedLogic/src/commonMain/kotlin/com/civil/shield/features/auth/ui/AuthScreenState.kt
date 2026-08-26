package com.civil.shield.features.auth.ui

/**
 * UI State for AuthScreen.
 */
data class AuthScreenState(
    val selectedLanguage: String = "RO",
    val availableLanguages: List<String> = listOf("RO", "EN", "HU"),
    val isLanguageDropdownExpanded: Boolean = false,
    val isLoading: Boolean = false
)

/**
 * User actions/events triggered from the AuthScreen UI.
 */
sealed interface AuthUiAction {
    data class SelectLanguage(val language: String) : AuthUiAction
    data class SetDropdownExpanded(val expanded: Boolean) : AuthUiAction
    data object Authenticate : AuthUiAction
    data object ClickTerms : AuthUiAction
    data object ClickPrivacy : AuthUiAction
}
