package com.civil.shield.auth

/**
 * Multiplatform PKCE cryptographic utilities (RFC 7636).
 */
expect object PkceCrypto {
    /**
     * Generates a high-entropy cryptographic random string (43-128 characters).
     */
    fun generateCodeVerifier(): String

    /**
     * Calculates the SHA-256 hash of the code verifier and Base64URL encodes it without padding.
     */
    fun generateCodeChallenge(verifier: String): String

    /**
     * Generates a random state string to prevent CSRF attacks.
     */
    fun generateState(): String
}
