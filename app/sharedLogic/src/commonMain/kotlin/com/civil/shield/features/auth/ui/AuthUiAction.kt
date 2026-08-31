package com.civil.shield.features.auth.ui

/**
 * User actions/events triggered from the AuthScreen UI.
 */
sealed interface AuthUiAction {
    data class SelectLanguage(val language: String) : AuthUiAction
    data class SetDropdownExpanded(val expanded: Boolean) : AuthUiAction
    data class Authenticate(val connection: String? = null) : AuthUiAction
    data class HandleCallback(val code: String, val state: String) : AuthUiAction
    data class SetError(val message: String) : AuthUiAction
    data object ClearAuthorizeUrl : AuthUiAction
    data object Logout : AuthUiAction
    data object ClickTerms : AuthUiAction
    data object ClickPrivacy : AuthUiAction
}
