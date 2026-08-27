package com.civil.shield.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Shared Auth0 configuration constants for CivilShield.
 */
object Auth0Config {
    const val DOMAIN = "civil-shield.eu.auth0.com"
    const val CLIENT_ID = "9yGRT4X7ShLhitIN8g2Y8I9ZpF177LZk"
    const val AUDIENCE = "https://api.civilshield.com"
    const val REDIRECT_URI = "civilshield://auth/callback"
    const val LOGOUT_REDIRECT_URI = "civilshield://auth/logout"

    const val ANDROID_CALLBACK_URI = "civilshield://auth/callback"
    const val ANDROID_LOGOUT_URI = "civilshield://auth/logout"
    const val ISSUER = "https://$DOMAIN/"
    const val JWKS_URL = "https://$DOMAIN/.well-known/jwks.json"
}

/**
 * User Profile DTO returned by backend or parsed from ID token.
 */
@Serializable
data class UserProfileDto(
    @SerialName("sub")
    val userId: String,
    @SerialName("email")
    val email: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("picture")
    val pictureUrl: String? = null,
    @SerialName("email_verified")
    val isEmailVerified: Boolean = false,
    @SerialName("roles")
    val roles: List<String> = emptyList()
)

/**
 * Token response returned by OAuth token endpoint.
 */
@Serializable
data class AuthTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("id_token")
    val idToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("token_type")
    val tokenType: String = "Bearer",
    @SerialName("expires_in")
    val expiresIn: Long = 86400
)

/**
 * Standard API error response model.
 */
@Serializable
data class AuthErrorResponse(
    @SerialName("error")
    val error: String,
    @SerialName("error_description")
    val errorDescription: String? = null
)
