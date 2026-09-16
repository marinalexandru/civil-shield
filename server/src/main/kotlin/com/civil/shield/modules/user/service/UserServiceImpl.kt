package com.civil.shield.modules.user.service

import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.shared.config.Auth0Properties
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Service
class UserServiceImpl(
    private val auth0Properties: Auth0Properties
) : UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build()
    private val json = Json { ignoreUnknownKeys = true }

    override fun getCurrentUserProfile(jwt: Jwt?, authorizationHeader: String?): UserProfileDto {
        val userId = jwt?.subject ?: "unknown"
        val email = jwt?.getClaimAsString("email")
        val name = jwt?.getClaimAsString("name")
        val picture = jwt?.getClaimAsString("picture")
        val isEmailVerified = jwt?.getClaimAsBoolean("email_verified") ?: false

        if ((email == null || name == null) && !authorizationHeader.isNullOrBlank()) {
            val bearerToken = authorizationHeader.removePrefix("Bearer ").trim()
            val fetchedProfile = fetchUserInfoFromAuth0(auth0Properties.domain, bearerToken)
            if (fetchedProfile != null) {
                return fetchedProfile
            }
        }

        return UserProfileDto(
            userId = userId,
            email = email,
            name = name,
            pictureUrl = picture,
            isEmailVerified = isEmailVerified
        )
    }

    override fun fetchUserInfoFromAuth0(domain: String, bearerToken: String): UserProfileDto? {
        val cleanDomain = domain.removePrefix("https://").removePrefix("http://").trimEnd('/')
        val url = "https://$cleanDomain/userinfo"

        return try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer $bearerToken")
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build()

            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() == 200) {
                val jsonElement = json.parseToJsonElement(response.body())
                val jsonObj = jsonElement.jsonObject

                val userId = jsonObj["sub"]?.jsonPrimitive?.content ?: "unknown"
                val email = jsonObj["email"]?.jsonPrimitive?.content
                val name = jsonObj["name"]?.jsonPrimitive?.content
                val picture = jsonObj["picture"]?.jsonPrimitive?.content
                val emailVerified = jsonObj["email_verified"]?.jsonPrimitive?.booleanOrNull ?: false

                UserProfileDto(
                    userId = userId,
                    email = email,
                    name = name,
                    pictureUrl = picture,
                    isEmailVerified = emailVerified
                )
            } else {
                logger.warn("Failed to fetch Auth0 userinfo, HTTP status: {}", response.statusCode())
                null
            }
        } catch (e: Exception) {
            logger.warn("Exception fetching Auth0 userinfo: {}", e.message)
            null
        }
    }
}
