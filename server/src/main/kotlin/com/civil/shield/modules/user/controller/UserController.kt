package com.civil.shield.modules.user.controller

import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.modules.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun getCurrentUser(
        @AuthenticationPrincipal jwt: Jwt?,
        @RequestHeader(value = "Authorization", required = false) authorizationHeader: String?
    ): ResponseEntity<UserProfileDto> {
        val userProfile = userService.getCurrentUserProfile(jwt, authorizationHeader)
        return ResponseEntity.ok(userProfile)
    }
}
