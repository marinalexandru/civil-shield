package com.civil.shield.modules.auth.service

interface AuthService {
    fun getAuthConfig(): Map<String, String>
}
