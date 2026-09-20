package com.civil.shield.auth

import com.civil.shield.core.auth.Auth0Config
import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.UserProfileDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single

@Single
class AuthRepository(
    private val authApiService: AuthApiService
) {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private var activePkceSession: PkceSession? = null

    fun startPkceLogin(
        redirectUri: String = Auth0Config.ANDROID_CALLBACK_URI,
        connection: String? = null,
        prompt: String? = "login"
    ): PkceSession {
        val session = Auth0PkceHelper.createPkceSession(
            redirectUri = redirectUri,
            connection = connection,
            prompt = prompt
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

            // Parse ID token claims (contains name, email, picture, email_verified)
            val idTokenProfile = tokenResponse.idToken?.let { IdTokenParser.parse(it) }

            // Fetch profile from backend with fallback to ID token profile if backend is unreachable or fails
            val backendProfile = try {
                authApiService.fetchUserInfo(tokenResponse.accessToken)
            } catch (e: Exception) {
                idTokenProfile ?: throw e
            }

            // Merge profiles: prefer backend attributes, but use ID token attributes for missing fields
            val mergedProfile = UserProfileDto(
                userId = backendProfile.userId.ifBlank { idTokenProfile?.userId ?: "unknown" },
                email = backendProfile.email ?: idTokenProfile?.email,
                name = backendProfile.name ?: idTokenProfile?.name,
                pictureUrl = backendProfile.pictureUrl ?: idTokenProfile?.pictureUrl,
                isEmailVerified = backendProfile.isEmailVerified || (idTokenProfile?.isEmailVerified == true),
                roles = backendProfile.roles.ifEmpty { idTokenProfile?.roles ?: emptyList() }
            )

            _authState.value = AuthState.Authenticated(
                user = mergedProfile,
                tokens = tokenResponse
            )
            activePkceSession = null
            Result.success(mergedProfile)
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

    suspend fun logout() {
        val currentTokens = (_authState.value as? AuthState.Authenticated)?.tokens
        try {
            val refreshToken = currentTokens?.refreshToken
            if (!refreshToken.isNullOrBlank()) {
                authApiService.revokeToken(refreshToken)
            }
        } catch (_: Exception) {
            // Proceed with clearing local authentication state even if network call fails
        } finally {
            activePkceSession = null
            _authState.value = AuthState.Unauthenticated
        }
    }
}
