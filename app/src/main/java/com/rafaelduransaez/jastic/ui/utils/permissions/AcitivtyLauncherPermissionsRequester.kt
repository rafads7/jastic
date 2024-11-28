package com.rafaelduransaez.jastic.ui.utils.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.common.Toast
import com.rafaelduransaez.jastic.ui.components.jAlertDialog.JAlertDialog
import com.rafaelduransaez.jastic.ui.utils.findActivity
import com.rafaelduransaez.jastic.ui.utils.openSettings
import com.rafaelduransaez.jastic.ui.utils.permissions.Companion.Companion.MIN_PERMISSIONS
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionDialogState.AllGranted
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionDialogState.GoToAppSettings
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionDialogState.None
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionDialogState.Rationale
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRequester(permissions: List<String>, onAllGranted: @Composable () -> Unit = {}) {

    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var initialPermissionRequestDone by rememberSaveable { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(permissions) {
        initialPermissionRequestDone = true
    }

    LifecycleResumeEffect(Unit) {
        showAlertDialog = true
        onPauseOrDispose { showAlertDialog = false }
    }

    if (showAlertDialog) {
        PermissionsDialog(
            permissionsState = permissionsState,
            initialPermissionRequestDone = initialPermissionRequestDone,
            onAllGranted = {
                if (initialPermissionRequestDone) Toast(R.string.str_thanks_for_permissions)
                onAllGranted()
            },
            onConfirmToRationale = {
                showAlertDialog = false
                permissionsState.launchMultiplePermissionRequest()
            },
            hideDialogAction = { showAlertDialog = false }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsDialog(
    permissionsState: MultiplePermissionsState,
    initialPermissionRequestDone: Boolean,
    onAllGranted: @Composable () -> Unit,
    onConfirmToRationale: () -> Unit,
    hideDialogAction: () -> Unit,
) {
    val textProvider = getPermissionsTextProvider(permissionsState, LocalContext.current)
    val state = getPermissionsDialogState(permissionsState, initialPermissionRequestDone)
    when (state) {
        GoToAppSettings -> GoToAppSettingsAppDialog(textProvider, hideDialogAction)
        Rationale -> RationaleDialog(textProvider, onConfirmToRationale)
        AllGranted -> onAllGranted()
        None -> Unit
    }
}

@Composable
fun GoToAppSettingsAppDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        confirmButtonTextId = R.string.str_go_to_settings,
        onConfirm = {
            onConfirm()
            context.findActivity()?.openSettings()
        }
    )
}

@Composable
fun RationaleDialog(
    permissionTextProvider: PermissionTextProvider,
    onConfirm: () -> Unit
) {
    JAlertDialog(
        title = permissionTextProvider.title,
        description = permissionTextProvider.description,
        onConfirm = onConfirm
    )
}

@OptIn(ExperimentalPermissionsApi::class)
fun getPermissionsDialogState(
    permissionsState: MultiplePermissionsState,
    initialPermissionRequestDone: Boolean
): PermissionDialogState {
    return when {
        permissionsState.allPermissionsGranted -> AllGranted
        permissionsState.shouldShowRationale -> Rationale
        initialPermissionRequestDone -> GoToAppSettings
        else -> Rationale
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsTextProvider(
    permissionsState: MultiplePermissionsState,
    context: Context
): PermissionTextProvider =
    if (permissionsState.revokedPermissions.size >= MIN_PERMISSIONS) {
        AllPermissionsTextProvider(context)
    } else {
        when (permissionsState.revokedPermissions.firstOrNull()?.permission) {
            ACCESS_FINE_LOCATION,
            ACCESS_BACKGROUND_LOCATION -> LocationPermissionTextProvider(context)

            POST_NOTIFICATIONS,
            ACCESS_NOTIFICATION_POLICY -> NotificationsPermissionTextProvider(context)

            READ_CONTACTS -> ContactsPermissionTextProvider(context)

            else -> UnknownPermissionTextProvider()
        }
    }

@Parcelize
sealed class PermissionDialogState : Parcelable {
    data object AllGranted : PermissionDialogState()
    data object Rationale : PermissionDialogState()
    data object GoToAppSettings : PermissionDialogState()
    data object None : PermissionDialogState()
}