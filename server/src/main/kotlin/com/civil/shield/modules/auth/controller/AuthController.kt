package com.civil.shield.modules.auth.controller

import com.civil.shield.modules.auth.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
}
