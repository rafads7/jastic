package com.rafaelduransaez.core.ui.permissions

import androidx.compose.runtime.saveable.Saver

sealed interface PermissionsDialogState {
    data object Granted : PermissionsDialogState
    data object None : PermissionsDialogState
    data object Rationale : PermissionsDialogState
    data object Settings : PermissionsDialogState
}

val PermissionsDialogStateSaver = Saver<PermissionsDialogState, String>(
    save = { dialogState ->
        when (dialogState) {
            PermissionsDialogState.None -> "None"
            PermissionsDialogState.Rationale -> "Rationale"
            PermissionsDialogState.Settings -> "Settings"
            PermissionsDialogState.Granted -> "Granted"
        }
    },
    restore = { savedValue ->
        when (savedValue) {
            "Rationale" -> PermissionsDialogState.Rationale
            "Settings" -> PermissionsDialogState.Settings
            "Granted" -> PermissionsDialogState.Granted
            "None" -> PermissionsDialogState.None
            else -> PermissionsDialogState.None
        }
    }
)