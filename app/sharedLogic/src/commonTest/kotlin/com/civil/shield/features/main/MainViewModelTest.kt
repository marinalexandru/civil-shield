package com.civil.shield.features.main

import com.civil.shield.auth.AuthApiService
import com.civil.shield.auth.AuthRepository
import com.civil.shield.core.auth.AuthTokenResponse
import com.civil.shield.core.auth.LogoutResponse
import com.civil.shield.core.auth.UserProfileDto
import com.civil.shield.features.main.ui.MainUiAction
import com.civil.shield.features.main.ui.MainViewModel
import com.civil.shield.navigation.AppDestination
import com.civil.shield.navigation.AppNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var navigator: AppNavigator
    private lateinit var authRepository: AuthRepository
    private lateinit var fakeAuthApiService: FakeAuthApiService

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        navigator = AppNavigator()
        fakeAuthApiService = FakeAuthApiService()
        authRepository = AuthRepository(fakeAuthApiService)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runTest {
        val viewModel = MainViewModel(authRepository, navigator)
        advanceUntilIdle()

        assertNull(viewModel.state.value.user)
        assertNull(viewModel.state.value.errorMessage)
    }

    @Test
    fun testLogoutClearsSessionAndReplacesRoot() = runTest {
        navigator.navigateTo(AppDestination.Main)
        val viewModel = MainViewModel(authRepository, navigator)
        advanceUntilIdle()

        viewModel.onAction(MainUiAction.Logout)
        advanceUntilIdle()

        assertEquals(1, fakeAuthApiService.logoutCallCount)
        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
        assertEquals(AppDestination.Auth, navigator.currentDestination)
    }

    @Test
    fun testLoginReplacesRootWithAuth() = runTest {
        navigator.navigateTo(AppDestination.Main)
        val viewModel = MainViewModel(authRepository, navigator)
        advanceUntilIdle()

        viewModel.onAction(MainUiAction.Login)
        advanceUntilIdle()

        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
        assertEquals(AppDestination.Auth, navigator.currentDestination)
    }

    @Test
    fun testClearError() = runTest {
        val viewModel = MainViewModel(authRepository, navigator)
        advanceUntilIdle()

        viewModel.onAction(MainUiAction.ClearError)
        assertNull(viewModel.state.value.errorMessage)
    }

    private class FakeAuthApiService : AuthApiService {
        var logoutCallCount = 0

        override suspend fun exchangeCodeForToken(
            code: String,
            codeVerifier: String,
            redirectUri: String
        ): AuthTokenResponse {
            return AuthTokenResponse(
                accessToken = "test-token",
                idToken = "test-id-token",
                tokenType = "Bearer",
                expiresIn = 3600
            )
        }

        override suspend fun fetchUserInfo(accessToken: String): UserProfileDto {
            return UserProfileDto(
                userId = "auth0|test",
                name = "Test User",
                email = "test@example.com"
            )
        }

        override suspend fun logout(accessToken: String?): LogoutResponse {
            logoutCallCount++
            return LogoutResponse(success = true, message = "Logged out")
        }
    }
}
