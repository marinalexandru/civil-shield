package com.civil.shield.auth

import com.civil.shield.core.auth.Auth0Config
import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.UserProfileDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository(
    private val authApiService: AuthApiService = AuthApiServiceImpl()
) {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var activePkceSession: PkceSession? = null

    fun startPkceLogin(
        redirectUri: String = Auth0Config.ANDROID_CALLBACK_URI,
        connection: String? = null
    ): PkceSession {
        val session = Auth0PkceHelper.createPkceSession(
            redirectUri = redirectUri,
            connection = connection
        )
        activePkceSession = session
        _authState.value = AuthState.Authenticating
        return session
    }

    suspend fun handleCallback(
        code: String,
        state: String,
        redirectUri: String = Auth0Config.ANDROID_CALLBACK_URI
    ): Result<UserProfileDto> {
        val session = activePkceSession
        if (session == null || session.state != state) {
            val errorMsg = "State parameter mismatch or session expired"
            _authState.value = AuthState.Error(errorMsg)
            return Result.failure(IllegalStateException(errorMsg))
        }

        return try {
            val tokenResponse = authApiService.exchangeCodeForToken(
                code = code,
                codeVerifier = session.codeVerifier,
                redirectUri = redirectUri
            )

            val profile = authApiService.fetchUserInfo(tokenResponse.accessToken)

            _authState.value = AuthState.Authenticated(
                user = profile,
                tokens = tokenResponse
            )
            activePkceSession = null
            Result.success(profile)
        } catch (e: Exception) {
            val errorMsg = e.message ?: "Authentication failed"
            _authState.value = AuthState.Error(errorMsg)
            Result.failure(e)
        }
    }

    suspend fun exchangeCodeForToken(
        code: String,
        codeVerifier: String,
        redirectUri: String
    ): AuthTokenResponse {
        return authApiService.exchangeCodeForToken(
            code = code,
            codeVerifier = codeVerifier,
            redirectUri = redirectUri
        )
    }

    suspend fun fetchUserInfo(accessToken: String): UserProfileDto {
        return authApiService.fetchUserInfo(accessToken)
    }

    suspend fun logout(accessToken: String? = null) {
        val currentAccessToken = accessToken ?: (_authState.value as? AuthState.Authenticated)?.tokens?.accessToken
        try {
            authApiService.logout(currentAccessToken)
        } catch (_: Exception) {
            // Proceed with clearing local authentication state even if network call fails
        } finally {
            activePkceSession = null
            _authState.value = AuthState.Unauthenticated
        }
    }
}
