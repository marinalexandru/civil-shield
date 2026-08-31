package com.civil.shield

import com.civil.shield.core.auth.Auth0Config
import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.plugins.configureSecurity
import com.civil.shield.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(
        factory = Netty,
        port = port,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()

    routing {
        get("/") {
            call.respondText(sayHello("CivilShield Backend"))
        }

        route("/api/v1") {
            // Public endpoint returning Auth0 metadata
            get("/auth/config") {
                call.respond(
                    mapOf(
                        "domain" to Auth0Config.DOMAIN,
                        "clientId" to Auth0Config.CLIENT_ID,
                        "audience" to Auth0Config.AUDIENCE
                    )
                )
            }

            // Logout endpoint to invalidate session or acknowledge user logout
            post("/auth/logout") {
                call.respond(
                    LogoutResponse(
                        success = true,
                        message = "Logged out successfully"
                    )
                )
            }

            get("/auth/logout") {
                call.respond(
                    LogoutResponse(
                        success = true,
                        message = "Logged out successfully"
                    )
                )
            }

            // Protected endpoint requiring valid Auth0 JWT
            authenticate("auth0") {
                get("/user/me") {
                    val principal = call.principal<JWTPrincipal>()
                    val subject = principal?.payload?.subject ?: "unknown"
                    val email = principal?.payload?.getClaim("email")?.asString()
                    val name = principal?.payload?.getClaim("name")?.asString()

                    call.respond(
                        UserProfileDto(
                            userId = subject,
                            email = email,
                            name = name,
                            isEmailVerified = principal?.payload?.getClaim("email_verified")
                                ?.asBoolean() ?: false
                        )
                    )
                }
            }
        }
    }
}
