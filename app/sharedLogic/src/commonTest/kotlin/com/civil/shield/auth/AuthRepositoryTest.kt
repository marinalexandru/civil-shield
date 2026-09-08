package com.civil.shield.auth

import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.core.auth.UserProfileDto
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AuthRepositoryTest {

    private lateinit var fakeAuthApiService: FakeAuthApiService
    private lateinit var authRepository: AuthRepository

    // Real Auth0 ID token with sub, name, email, picture
    private val sampleIdToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkRsYS10aDR1TmJVS3RoR3ZwMlBHMCJ9." +
            "eyJnaXZlbl9uYW1lIjoiYW5kdSIsImZhbWlseV9uYW1lIjoibWFyaW4iLCJuaWNrbmFtZSI6Im1hcmluLmFsZXhh" +
            "bmRydS5hbmR1IiwibmFtZSI6ImFuZHUgbWFyaW4iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNv" +
            "bnRlbnQuY29tL2EvQUNnOG9jS0IwX19ZRllBTHdtTjRSRmJHd09oTlF0UjBJOHNGbEE0SWZRSmRpS2YwX3h4UnFq" +
            "Tl89czk2LWMiLCJ1cGRhdGVkX2F0IjoiMjAyNi0wOS0wOFQyMDoyODowMi4wNjFaIiwiZW1haWwiOiJtYXJpbi5h" +
            "bGV4YW5kcnUuYW5kdUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6Ly9jaXZp" +
            "bC1zaGllbGQuZXUuYXV0aDAuY29tLyIsImF1ZCI6Ijl5R1JUNFg3U2hMaGl0SU44ZzJZOEk5WnBGMTc3TFprIiwi" +
            "c3ViIjoiZ29vZ2xlLW9hdXRoMnwxMDIwMTAxNzcxMTMzMjMzMjQ5NDUiLCJpYXQiOjE3ODg4OTkyODUsImV4cCI6" +
            "MTc4ODkzNTI4NSwic2lkIjoiVGkzcVVWWmYxT2FKWGM0eExUM3JDc1g5MWpxTzJ0RFkifQ.u6cXd-xfBBBm7u-O" +
            "MDz0OzZVeFe7yH_-jNuPYPfkyt_LK7Ww7FTOoNf7nsfzrWsST35phVPEdjeebf_dPBtLBq4zlOzjiD2H7A_yTn8c" +
            "_LSDNqYZ34sWff1fs1FdyuTG9VkOTnC6ljOgHpBSGOFBSutjR9Zj7BO_KtFWt1nnYEzseGAuI4KejcYNWXs3Fm1E" +
            "6xjjb0utpgAZDyMHGMziELB6tKEZkgH8OlP_1BPSrjJQeeN8mrkx4eaIAz3mgCpGxq_G_yJLLa6urkSTglK0YRGI" +
            "fvU0HT6TaSUjJC-5k0mJ3ZBOKhoXb2vYOCcoewaSCNq0avVQsJNgrhznbCgP5g"

    @BeforeTest
    fun setUp() {
        fakeAuthApiService = FakeAuthApiService()
        authRepository = AuthRepository(fakeAuthApiService)
    }

    @Test
    fun testHandleCallbackMergesBackendAndIdToken() = runTest {
        val session = authRepository.startPkceLogin()

        fakeAuthApiService.tokenResponse = AuthTokenResponse(
            accessToken = "access_token_123",
            idToken = sampleIdToken,
            tokenType = "Bearer",
            expiresIn = 86400
        )
        // Backend returns user with missing name/email
        fakeAuthApiService.userProfileResponse = UserProfileDto(
            userId = "google-oauth2|102010177113323324945",
            email = null,
            name = null,
            isEmailVerified = false
        )

        val result = authRepository.handleCallback(
            code = "test_code",
            state = session.state
        )

        assertTrue(result.isSuccess)
        val profile = result.getOrThrow()
        assertEquals("google-oauth2|102010177113323324945", profile.userId)
        assertEquals("andu marin", profile.name)
        assertEquals("marin.alexandru.andu@gmail.com", profile.email)
        assertTrue(profile.isEmailVerified)
        assertIs<AuthState.Authenticated>(authRepository.authState.value)
    }

    @Test
    fun testHandleCallbackFallsBackToIdTokenWhenBackendFails() = runTest {
        val session = authRepository.startPkceLogin()

        fakeAuthApiService.tokenResponse = AuthTokenResponse(
            accessToken = "access_token_123",
            idToken = sampleIdToken,
            tokenType = "Bearer",
            expiresIn = 86400
        )
        fakeAuthApiService.fetchUserInfoError = IllegalStateException("Backend 401 Unauthorized")

        val result = authRepository.handleCallback(
            code = "test_code",
            state = session.state
        )

        assertTrue(result.isSuccess)
        val profile = result.getOrThrow()
        assertEquals("andu marin", profile.name)
        assertEquals("marin.alexandru.andu@gmail.com", profile.email)
        assertIs<AuthState.Authenticated>(authRepository.authState.value)
    }

    @Test
    fun testHandleCallbackFailsOnStateMismatch() = runTest {
        authRepository.startPkceLogin()

        val result = authRepository.handleCallback(
            code = "test_code",
            state = "wrong_state"
        )

        assertTrue(result.isFailure)
        assertIs<AuthState.Error>(authRepository.authState.value)
    }

    private class FakeAuthApiService : AuthApiService {
        var tokenResponse: AuthTokenResponse = AuthTokenResponse(
            accessToken = "token",
            idToken = null,
            tokenType = "Bearer",
            expiresIn = 3600
        )
        var userProfileResponse: UserProfileDto = UserProfileDto(userId = "user_1")
        var fetchUserInfoError: Throwable? = null

        override suspend fun exchangeCodeForToken(
            code: String,
            codeVerifier: String,
            redirectUri: String
        ): AuthTokenResponse = tokenResponse

        override suspend fun fetchUserInfo(accessToken: String): UserProfileDto {
            fetchUserInfoError?.let { throw it }
            return userProfileResponse
        }

        override suspend fun logout(accessToken: String?): LogoutResponse {
            return LogoutResponse(success = true, message = "Logged out")
        }
    }
}
