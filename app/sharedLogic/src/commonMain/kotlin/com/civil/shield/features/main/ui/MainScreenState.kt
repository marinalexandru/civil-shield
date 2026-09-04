package com.civil.shield.features.main.ui

import com.civil.shield.core.auth.UserProfileDto

/**
 * Immutable UI State for the MainScreen.
 */
data class MainScreenState(
    val user: UserProfileDto? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
