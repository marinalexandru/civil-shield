package com.civil.shield.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppNavigatorTest {

    @Test
    fun testInitialDestinationIsAuth() {
        val navigator = AppNavigator()
        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
        assertEquals(AppDestination.Auth, navigator.currentDestination)
    }

    @Test
    fun testNavigateToMain() {
        val navigator = AppNavigator()
        navigator.navigateTo(AppDestination.Main)

        assertEquals(listOf(AppDestination.Auth, AppDestination.Main), navigator.backStack.value)
        assertEquals(AppDestination.Main, navigator.currentDestination)
    }

    @Test
    fun testPopBackStack() {
        val navigator = AppNavigator()
        navigator.navigateTo(AppDestination.Main)

        val popped = navigator.pop()
        assertTrue(popped)
        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
        assertEquals(AppDestination.Auth, navigator.currentDestination)

        // Cannot pop root
        val poppedRoot = navigator.pop()
        assertFalse(poppedRoot)
        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
    }

    @Test
    fun testReplaceRoot() {
        val navigator = AppNavigator()
        navigator.navigateTo(AppDestination.Main)

        navigator.replaceRoot(AppDestination.Auth)
        assertEquals(listOf(AppDestination.Auth), navigator.backStack.value)
        assertEquals(AppDestination.Auth, navigator.currentDestination)
    }
}
