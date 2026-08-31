package com.civil.shield.core.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Standard API response model for logout operations.
 */
@Serializable
data class LogoutResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String
)
