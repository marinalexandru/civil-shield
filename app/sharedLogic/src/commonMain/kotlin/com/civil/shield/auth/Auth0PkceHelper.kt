package com.civil.shield.auth

import com.civil.shield.core.auth.Auth0Config

data class PkceSession(
    val codeVerifier: String,
    val state: String,
    val authorizeUrl: String
)

object Auth0PkceHelper {

    /**
     * Creates a new PKCE session with random verifier and state, and returns the Auth0 Universal Login URL.
     */
    fun createPkceSession(
        redirectUri: String = Auth0Config.ANDROID_CALLBACK_URI,
        connection: String? = null,
        prompt: String? = null
    ): PkceSession {
        val verifier = PkceCrypto.generateCodeVerifier()
        val state = PkceCrypto.generateState()
        val challenge = PkceCrypto.generateCodeChallenge(verifier)

        val params = mutableListOf(
            "client_id" to Auth0Config.CLIENT_ID,
            "response_type" to "code",
            "redirect_uri" to redirectUri,
            "code_challenge" to challenge,
            "code_challenge_method" to "S256",
            "scope" to "openid profile email offline_access",
            "audience" to Auth0Config.AUDIENCE,
            "state" to state
        )

        if (!connection.isNullOrBlank()) {
            params.add("connection" to connection)
        }

        if (!prompt.isNullOrBlank()) {
            params.add("prompt" to prompt)
        }

        val queryString = params.joinToString("&") { (key, value) ->
            "$key=${encodeQueryParam(value)}"
        }

        val authorizeUrl = "https://${Auth0Config.DOMAIN}/authorize?$queryString"

        return PkceSession(
            codeVerifier = verifier,
            state = state,
            authorizeUrl = authorizeUrl
        )
    }

    /**
     * Builds the Auth0 logout URL.
     */
    fun buildLogoutUrl(returnTo: String = Auth0Config.ANDROID_LOGOUT_URI): String {
        return "https://${Auth0Config.DOMAIN}/v2/logout?client_id=${Auth0Config.CLIENT_ID}&returnTo=${encodeQueryParam(returnTo)}"
    }

    private fun encodeQueryParam(value: String): String {
        // Safe encoding for standard URI query parameters
        val allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~"
        val out = StringBuilder()
        for (char in value) {
            if (char in allowed) {
                out.append(char)
            } else {
                for (byte in char.toString().encodeToByteArray()) {
                    out.append("%${(byte.toInt() and 0xFF).toString(16).uppercase().padStart(2, '0')}")
                }
            }
        }
        return out.toString()
    }
}
