package com.civil.shield.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Single

/**
 * State-driven shared navigator managing the backstack in commonMain.
 * Can be observed by Jetpack Compose (via Navigation 3 NavDisplay) or SwiftUI (via NavigationStack).
 */
@Single
open class AppNavigator {

    private val _backStack = MutableStateFlow<List<AppDestination>>(listOf(AppDestination.Auth))
    val backStack: StateFlow<List<AppDestination>> = _backStack.asStateFlow()

    val currentDestination: AppDestination
        get() = _backStack.value.lastOrNull() ?: AppDestination.Auth

    fun navigateTo(destination: AppDestination) {
        _backStack.update { current -> current + destination }
    }

    fun pop(): Boolean {
        var popped = false
        _backStack.update { current ->
            if (current.size > 1) {
                popped = true
                current.dropLast(1)
            } else {
                current
            }
        }
        return popped
    }

    fun replaceRoot(destination: AppDestination) {
        _backStack.update { listOf(destination) }
    }
}
