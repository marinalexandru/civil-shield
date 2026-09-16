package com.civil.shield.modules.auth.service

import com.civil.shield.core.auth.LogoutResponse

interface AuthService {
    fun getAuthConfig(): Map<String, String>
    fun logout(): LogoutResponse
}
