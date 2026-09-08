package com.civil.shield.plugins

import com.auth0.jwk.JwkProviderBuilder
import com.civil.shield.core.auth.Auth0Config
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import java.net.URI
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {
    val domain = System.getenv("AUTH0_DOMAIN") ?: Auth0Config.DOMAIN
    val audience = System.getenv("AUTH0_AUDIENCE") ?: Auth0Config.AUDIENCE
    val issuer = if (domain.startsWith("http")) domain else "https://$domain/"
    val jwksUrl = "https://${domain.removePrefix("https://").removePrefix("http://").trimEnd('/')}/.well-known/jwks.json"

    val jwkProvider = JwkProviderBuilder(URI.create(jwksUrl).toURL())
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .timeouts(5000, 5000)
        .build()

    install(Authentication) {
        jwt("auth0") {
            realm = "CivilShield"
            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
            }
            validate { credential ->
                val credentialAudience = credential.payload.audience
                if (credentialAudience.contains(audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf("error" to "unauthorized", "message" to "Token is invalid or expired")
                )
            }
        }
    }
}
