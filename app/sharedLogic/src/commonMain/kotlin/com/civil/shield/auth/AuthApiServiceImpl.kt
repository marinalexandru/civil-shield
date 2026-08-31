package com.civil.shield.auth

import com.civil.shield.core.auth.Auth0Config
import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.core.config.ServerConfig
import com.civil.shield.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters

/**
 * Implementation of [AuthApiService] using Ktor HttpClient.
 */
class AuthApiServiceImpl(
    private val httpClient: HttpClient = HttpClientFactory.create(),
    private val backendBaseUrl: String = ServerConfig.BASE_URL
) : AuthApiService {

    override suspend fun exchangeCodeForToken(
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

    override suspend fun fetchUserInfo(accessToken: String): UserProfileDto {
        val response = httpClient.get("$backendBaseUrl/api/v1/user/me") {
            header("Authorization", "Bearer $accessToken")
        }

        if (response.status != HttpStatusCode.OK) {
            val body = response.bodyAsText()
            throw IllegalStateException("Backend /user/me failed (${response.status}): $body")
        }

        return response.body<UserProfileDto>()
    }

    override suspend fun logout(accessToken: String?): LogoutResponse {
        val response = httpClient.post("$backendBaseUrl/api/v1/auth/logout") {
            if (!accessToken.isNullOrBlank()) {
                header("Authorization", "Bearer $accessToken")
            }
        }

        if (response.status != HttpStatusCode.OK) {
            val body = response.bodyAsText()
            throw IllegalStateException("Backend /auth/logout failed (${response.status}): $body")
        }

        return response.body<LogoutResponse>()
    }
}
