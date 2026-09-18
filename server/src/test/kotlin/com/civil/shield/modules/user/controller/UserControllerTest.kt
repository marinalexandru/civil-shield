package com.civil.shield.modules.user.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var jwtDecoder: JwtDecoder

    @Test
    fun testProtectedUserMeEndpointUnauthorizedWithoutToken() {
        mockMvc.perform(get("/api/v1/user/me"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun testProtectedUserMeEndpointWithToken() {
        mockMvc.perform(
            get("/api/v1/user/me")
                .with(
                    jwt().jwt { builder ->
                        builder
                            .subject("auth0|123456")
                            .claim("email", "alex@civilshield.com")
                            .claim("name", "Alexandru Marin")
                            .claim("picture", "https://avatar.png")
                            .claim("email_verified", true)
                    }
                )
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.sub").value("auth0|123456"))
            .andExpect(jsonPath("$.email").value("alex@civilshield.com"))
            .andExpect(jsonPath("$.name").value("Alexandru Marin"))
            .andExpect(jsonPath("$.picture").value("https://avatar.png"))
            .andExpect(jsonPath("$.email_verified").value(true))
    }
}
