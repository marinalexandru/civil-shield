package com.civil.shield.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe destination routes for CivilShield.
 * Maintained in commonMain so navigation state is fully shared across platforms.
 */
@Serializable
sealed interface AppDestination {

    @Serializable
    data object Auth : AppDestination

    @Serializable
    data object Main : AppDestination
}
