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
import java.net.URI
import kotlinx.serialization.json.Json

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
                val domain = System.getenv("AUTH0_DOMAIN") ?: Auth0Config.DOMAIN
                val audience = System.getenv("AUTH0_AUDIENCE") ?: Auth0Config.AUDIENCE
                call.respond(
                    mapOf(
                        "domain" to domain,
                        "clientId" to Auth0Config.CLIENT_ID,
                        "audience" to audience
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
                    var email = principal?.payload?.getClaim("email")?.asString()
                    var name = principal?.payload?.getClaim("name")?.asString()
                    var pictureUrl = principal?.payload?.getClaim("picture")?.asString()
                    var isEmailVerified = principal?.payload?.getClaim("email_verified")
                        ?.asBoolean() ?: false

                    // Auth0 access tokens for APIs do not include profile claims (email, name, picture) by default.
                    // If claims are missing, fetch userinfo from Auth0 using the incoming Bearer token.
                    if (email == null || name == null) {
                        val domain = System.getenv("AUTH0_DOMAIN") ?: Auth0Config.DOMAIN
                        val cleanDomain = domain.removePrefix("https://").removePrefix("http://").trimEnd('/')
                        val authHeader = call.request.headers["Authorization"]
                        if (!authHeader.isNullOrBlank()) {
                            try {
                                val url = URI.create("https://$cleanDomain/userinfo").toURL()
                                val conn = url.openConnection() as java.net.HttpURLConnection
                                conn.connectTimeout = 5000
                                conn.readTimeout = 5000
                                conn.requestMethod = "GET"
                                conn.setRequestProperty("Authorization", authHeader)
                                conn.setRequestProperty("Accept", "application/json")
                                if (conn.responseCode == 200) {
                                    val text = conn.inputStream.bufferedReader().readText()
                                    val json = Json { ignoreUnknownKeys = true }
                                    val userInfo = json.decodeFromString<UserProfileDto>(text)
                                    if (email == null) email = userInfo.email
                                    if (name == null) name = userInfo.name
                                    if (pictureUrl == null) pictureUrl = userInfo.pictureUrl
                                    isEmailVerified = isEmailVerified || userInfo.isEmailVerified
                                }
                            } catch (_: Exception) {
                                // Fall back to claims present in token
                            }
                        }
                    }

                    call.respond(
                        UserProfileDto(
                            userId = subject,
                            email = email,
                            name = name,
                            pictureUrl = pictureUrl,
                            isEmailVerified = isEmailVerified
                        )
                    )
                }
            }
        }
    }
}
