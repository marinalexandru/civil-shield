package com.civil.shield.features.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.civil.shield.auth.AuthRepository
import com.civil.shield.auth.AuthState
import com.civil.shield.navigation.AppDestination
import com.civil.shield.navigation.AppNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

/**
 * Shared KMP ViewModel managing MainScreen state and actions.
 */
@KoinViewModel
open class MainViewModel(
    private val authRepository: AuthRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    init {
        authRepository.authState
            .onEach { authState ->
                when (authState) {
                    is AuthState.Authenticated -> {
                        _state.update {
                            it.copy(username = authState.user.name ?: authState.user.email)
                        }
                    }
                    else -> {
                        _state.update { it.copy(username = null) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MainUiAction) {
        when (action) {
            MainUiAction.Logout -> logout()
            MainUiAction.NavigateBack -> navigator.pop()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authRepository.logout()
            _state.update { it.copy(isLoading = false) }
            navigator.replaceRoot(AppDestination.Auth)
        }
    }
}
