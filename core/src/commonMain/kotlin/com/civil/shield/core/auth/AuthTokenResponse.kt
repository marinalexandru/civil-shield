package com.civil.shield.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
