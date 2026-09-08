package com.civil.shield.auth

import com.civil.shield.core.auth.UserProfileDto
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Multiplatform utility to decode and parse identity claims from an OpenID Connect ID Token.
 */
object IdTokenParser {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    /**
     * Decodes the payload section of a JWT (Header.Payload.Signature) into a [UserProfileDto].
     * Returns null if the token is malformed or decoding fails.
     */
    @OptIn(ExperimentalEncodingApi::class)
    fun parse(idToken: String): UserProfileDto? {
        val parts = idToken.split(".")
        if (parts.size < 2) return null

        return try {
            val payloadBase64 = parts[1]
            val decodedBytes = Base64.UrlSafe
                .withPadding(Base64.PaddingOption.ABSENT_OPTIONAL)
                .decode(payloadBase64)
            val decodedJson = decodedBytes.decodeToString()

            json.decodeFromString<UserProfileDto>(decodedJson)
        } catch (_: Exception) {
            null
        }
    }
}
