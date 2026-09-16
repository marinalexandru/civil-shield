package com.civil.shield.shared.config

import com.civil.shield.core.auth.Auth0Config
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth0")
class Auth0Properties(
    var domain: String = Auth0Config.DOMAIN,
    var clientId: String = Auth0Config.CLIENT_ID,
    var audience: String = Auth0Config.AUDIENCE
)
