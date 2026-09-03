package com.civil.shield.features.main.ui

/**
 * User actions/events triggered from the MainScreen UI.
 */
sealed interface MainUiAction {
    data object Logout : MainUiAction
    data object NavigateBack : MainUiAction
}
