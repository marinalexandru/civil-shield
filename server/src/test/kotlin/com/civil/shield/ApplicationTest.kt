package com.civil.shield

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(sayHello("CivilShield Backend"), response.bodyAsText())
    }

    @Test
    fun testAuthConfigEndpoint() = testApplication {
        application {
            module()
        }
        val response = client.get("/api/v1/auth/config")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("civil-shield.eu.auth0.com"))
    }

    @Test
    fun testProtectedUserMeEndpointUnauthorizedWithoutToken() = testApplication {
        application {
            module()
        }
        val response = client.get("/api/v1/user/me")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun testLogoutEndpoint() = testApplication {
        application {
            module()
        }
        val postResponse = client.post("/api/v1/auth/logout")
        assertEquals(HttpStatusCode.OK, postResponse.status)
        assertTrue(postResponse.bodyAsText().contains("Logged out successfully"))

        val getResponse = client.get("/api/v1/auth/logout")
        assertEquals(HttpStatusCode.OK, getResponse.status)
        assertTrue(getResponse.bodyAsText().contains("Logged out successfully"))
    }
}