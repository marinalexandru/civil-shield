package com.civil.shield.core.auth

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
