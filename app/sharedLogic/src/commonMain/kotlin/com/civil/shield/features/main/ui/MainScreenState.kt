package com.civil.shield.features.main.ui

/**
 * Immutable UI State for the MainScreen.
 */
data class MainScreenState(
    val username: String? = null,
    val isLoading: Boolean = false
)
