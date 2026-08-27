package com.civil.shield.auth

import com.civil.shield.core.auth.Auth0Config
import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.UserProfileDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data object Authenticating : AuthState
    data class Authenticated(
        val user: UserProfileDto,
        val tokens: AuthTokenResponse
    ) : AuthState
    data class Error(val message: String) : AuthState
}

class AuthRepository(
    private val httpClient: HttpClient = createDefaultHttpClient()
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
            val tokenResponse = exchangeCodeForToken(
                code = code,
                codeVerifier = session.codeVerifier,
                redirectUri = redirectUri
            )

            val profile = fetchUserInfo(tokenResponse.accessToken)

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
        val response = httpClient.submitForm(
            url = "https://${Auth0Config.DOMAIN}/oauth/token",
            formParameters = Parameters.build {
                append("grant_type", "authorization_code")
                append("client_id", Auth0Config.CLIENT_ID)
                append("code_verifier", codeVerifier)
                append("code", code)
                append("redirect_uri", redirectUri)
            }
        )

        if (response.status != HttpStatusCode.OK) {
            val body = response.bodyAsText()
            throw IllegalStateException("Token exchange failed (${response.status}): $body")
        }

        return response.body<AuthTokenResponse>()
    }

    suspend fun fetchUserInfo(accessToken: String): UserProfileDto {
        val response = httpClient.get("https://${Auth0Config.DOMAIN}/userinfo") {
            header("Authorization", "Bearer $accessToken")
        }

        if (response.status != HttpStatusCode.OK) {
            val body = response.bodyAsText()
            throw IllegalStateException("UserInfo request failed (${response.status}): $body")
        }

        return response.body<UserProfileDto>()
    }

    suspend fun fetchBackendProfile(accessToken: String, backendBaseUrl: String = "http://10.0.2.2:8080"): UserProfileDto {
        val response = httpClient.get("$backendBaseUrl/api/v1/user/me") {
            header("Authorization", "Bearer $accessToken")
        }

        if (response.status != HttpStatusCode.OK) {
            val body = response.bodyAsText()
            throw IllegalStateException("Backend /user/me failed (${response.status}): $body")
        }

        return response.body<UserProfileDto>()
    }

    fun logout() {
        activePkceSession = null
        _authState.value = AuthState.Unauthenticated
    }

    companion object {
        fun createDefaultHttpClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    })
                }
            }
        }
    }
}
