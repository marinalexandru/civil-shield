package com.civil.shield.modules.auth.service

import com.civil.shield.shared.config.Auth0Properties
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val auth0Properties: Auth0Properties
) : AuthService {

    override fun getAuthConfig(): Map<String, String> {
        return mapOf(
            "domain" to auth0Properties.domain,
            "clientId" to auth0Properties.clientId,
            "audience" to auth0Properties.audience
        )
    }
}
