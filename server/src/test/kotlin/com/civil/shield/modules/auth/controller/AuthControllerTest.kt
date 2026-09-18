package com.civil.shield.modules.auth.controller

import com.civil.shield.core.auth.Auth0Config
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var jwtDecoder: JwtDecoder

    @Test
    fun testAuthConfigEndpoint() {
        mockMvc.perform(get("/api/v1/auth/config"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.domain").value(Auth0Config.DOMAIN))
            .andExpect(jsonPath("$.clientId").value(Auth0Config.CLIENT_ID))
            .andExpect(jsonPath("$.audience").value(Auth0Config.AUDIENCE))
    }

    @Test
    fun testLogoutEndpoint() {
        mockMvc.perform(post("/api/v1/auth/logout"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Logged out successfully"))

        mockMvc.perform(get("/api/v1/auth/logout"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Logged out successfully"))
    }
}
