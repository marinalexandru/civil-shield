package com.civil.shield.modules.auth.controller

import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.modules.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @GetMapping("/config")
    fun getConfig(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(authService.getAuthConfig())
    }

    @PostMapping("/logout")
    fun postLogout(): ResponseEntity<LogoutResponse> {
        return ResponseEntity.ok(authService.logout())
    }

    @GetMapping("/logout")
    fun getLogout(): ResponseEntity<LogoutResponse> {
        return ResponseEntity.ok(authService.logout())
    }
}
