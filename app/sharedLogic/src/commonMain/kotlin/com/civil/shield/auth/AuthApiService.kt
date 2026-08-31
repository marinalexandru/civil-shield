package com.civil.shield.auth

import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.core.auth.UserProfileDto

/**
 * Service handling low-level network API requests for authentication, token exchange, user profile, and session termination.
 */
interface AuthApiService {
    suspend fun exchangeCodeForToken(
        code: String,
        codeVerifier: String,
        redirectUri: String
    ): AuthTokenResponse

    suspend fun fetchUserInfo(accessToken: String): UserProfileDto

    suspend fun logout(accessToken: String? = null): LogoutResponse
}
