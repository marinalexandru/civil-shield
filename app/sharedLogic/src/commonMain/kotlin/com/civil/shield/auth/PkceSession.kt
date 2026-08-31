package com.civil.shield.auth

/**
 * Data class holding PKCE authorization state and generated URLs.
 */
data class PkceSession(
    val codeVerifier: String,
    val state: String,
    val authorizeUrl: String
)
