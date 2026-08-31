package com.civil.shield.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
