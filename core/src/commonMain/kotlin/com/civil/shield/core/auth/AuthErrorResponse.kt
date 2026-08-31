package com.civil.shield.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
