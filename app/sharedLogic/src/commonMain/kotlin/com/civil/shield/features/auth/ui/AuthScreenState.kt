package com.civil.shield.features.auth.ui

/**
 * UI State for AuthScreen.
 */
data class AuthScreenState(
    val selectedLanguage: String = "RO",
    val availableLanguages: List<String> = listOf("RO", "EN", "HU"),
    val isLanguageDropdownExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authorizeUrlToLaunch: String? = null
)
