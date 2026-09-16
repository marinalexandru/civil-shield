package com.civil.shield.modules.user.service

import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.shared.config.Auth0Properties
import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.client.RestClient
import java.time.Instant
import kotlin.test.assertEquals

class UserServiceTest {

    private val auth0Properties = Auth0Properties(
        domain = "test.auth0.com",
        clientId = "client-123",
        audience = "https://api.test.com"
    )
    private val userService: UserService = UserServiceImpl(auth0Properties, RestClient.create())

    @Test
    fun `returns profile directly from JWT claims`() {
        val jwt = Jwt.withTokenValue("mock-token")
            .header("alg", "none")
            .subject("user-456")
            .claim("email", "test@test.com")
            .claim("name", "Test User")
            .claim("picture", "https://img.png")
            .claim("email_verified", true)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build()

        val profile = userService.getCurrentUserProfile(jwt, "Bearer mock-token")

        assertEquals("user-456", profile.userId)
        assertEquals("test@test.com", profile.email)
        assertEquals("Test User", profile.name)
        assertEquals("https://img.png", profile.pictureUrl)
        assertEquals(true, profile.isEmailVerified)
    }

    @Test
    fun `returns unknown profile when JWT is null`() {
        val profile = userService.getCurrentUserProfile(null, null)

        assertEquals("unknown", profile.userId)
        assertEquals(null, profile.email)
        assertEquals(null, profile.name)
        assertEquals(false, profile.isEmailVerified)
    }
}
