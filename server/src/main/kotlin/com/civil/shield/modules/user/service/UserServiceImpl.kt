package com.civil.shield.modules.user.service

import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.shared.config.Auth0Properties
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientResponseException

@Service
class UserServiceImpl(
    private val auth0Properties: Auth0Properties,
    private val restClient: RestClient
) : UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)
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
        val url = "https://$domain/userinfo"

        return try {
            val responseBody = restClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $bearerToken")
                .retrieve()
                .body(String::class.java) ?: return null

            val jsonElement = json.parseToJsonElement(responseBody)
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
        } catch (e: RestClientResponseException) {
            logger.warn("Failed to fetch Auth0 userinfo, HTTP status: {}", e.statusCode)
            null
        } catch (e: Exception) {
            logger.warn("Exception fetching Auth0 userinfo: {}", e.message)
            null
        }
    }
}
