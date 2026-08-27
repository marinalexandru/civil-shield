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
    val jwkProvider = JwkProviderBuilder(URI.create(Auth0Config.ISSUER).toURL())
        .cached(10, 24, TimeUnit.HOURS)
        .rateLimited(10, 1, TimeUnit.MINUTES)
        .build()

    install(Authentication) {
        jwt("auth0") {
            realm = "CivilShield"
            verifier(jwkProvider, Auth0Config.ISSUER) {
                acceptLeeway(3)
            }
            validate { credential ->
                val audience = credential.payload.audience
                if (audience.contains(Auth0Config.AUDIENCE)) {
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
