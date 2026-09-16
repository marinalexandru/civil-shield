package com.civil.shield.modules.user.service

import com.civil.shield.core.auth.UserProfileDto
import org.springframework.security.oauth2.jwt.Jwt

interface UserService {
    fun getCurrentUserProfile(jwt: Jwt?, authorizationHeader: String?): UserProfileDto
    fun fetchUserInfoFromAuth0(domain: String, bearerToken: String): UserProfileDto?
}
