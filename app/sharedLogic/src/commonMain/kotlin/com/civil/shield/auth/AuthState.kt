package com.civil.shield.auth

import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.UserProfileDto

/**
 * Represents the current authentication state of the application.
 */
sealed interface AuthState {
    data object Unauthenticated : AuthState
    data object Authenticating : AuthState
    data class Authenticated(
        val user: UserProfileDto,
        val tokens: AuthTokenResponse
    ) : AuthState
    data class Error(val message: String) : AuthState
}
