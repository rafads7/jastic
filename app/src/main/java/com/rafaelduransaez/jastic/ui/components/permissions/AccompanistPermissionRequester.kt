package com.rafaelduransaez.jastic.ui.components.permissions

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rafaelduransaez.jastic.R
import com.rafaelduransaez.jastic.ui.components.jText.JText
import com.rafaelduransaez.jastic.ui.components.permissions.Companion.Companion.MIN_PERMISSIONS
import com.rafaelduransaez.jastic.ui.utils.findActivity
import com.rafaelduransaez.jastic.ui.utils.openSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AccompanistPermissionRequester() {

    val context = LocalContext.current

    var hasRequestedPermissions by rememberSaveable { mutableStateOf(false) }
    var permissionRequestCompleted by rememberSaveable { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(
        permissions = mutableListOf(ACCESS_NOTIFICATION_POLICY, ACCESS_FINE_LOCATION).apply {
/*            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                add(ACCESS_BACKGROUND_LOCATION)*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                add(POST_NOTIFICATIONS)
        }
    )

    LaunchedEffect(permissionsState.revokedPermissions) {
        if (hasRequestedPermissions) {
            permissionRequestCompleted = true
        }
    }

    CheckPermissions(
        permissionsState = permissionsState,
        permissionRequestCompleted = permissionRequestCompleted,
        onNeedToRequestPermissions = {
            RationaleDialog(
                permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                onRationaleActionClicked = {
                    permissionsState.launchMultiplePermissionRequest()
                    hasRequestedPermissions = true
                }
            )
        },
        onNeedToRequestPermissionsFromAppSettings = {
            GoToAppSettingsDialog(
                permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                onGoToAppSettingsClicked = {
                    context.findActivity()?.openSettings()
                }
            )
        },
        onShowPermissionsRationale = {
            RationaleDialog(
                permissionTextProvider = getPermissionsTextProvider(permissionsState, context),
                onRationaleActionClicked = {
                    permissionsState.launchMultiplePermissionRequest()
                }
            )
        },
        onAllPermissionsGranted = {
            if (hasRequestedPermissions)
                Toast(R.string.str_thanks_for_permissions)
        }
    )

}

@OptIn(ExperimentalPermissionsApi::class)
fun getPermissionsTextProvider(
    permissionsState: MultiplePermissionsState,
    context: Context
): PermissionTextProvider =
    if (permissionsState.revokedPermissions.size >= MIN_PERMISSIONS) {
        AllPermissionsTextProvider(context)
    } else when (permissionsState.revokedPermissions.firstOrNull()?.permission) {
        ACCESS_FINE_LOCATION,
        ACCESS_BACKGROUND_LOCATION -> LocationPermissionTextProvider(context)

        POST_NOTIFICATIONS,
        ACCESS_NOTIFICATION_POLICY -> NotificationsPermissionTextProvider(context)

        else -> UnknownPermissionTextProvider()
    }


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(
    permissionsState: MultiplePermissionsState,
    permissionRequestCompleted: Boolean,
    onAllPermissionsGranted: @Composable () -> Unit = {},
    onShowPermissionsRationale: @Composable () -> Unit = {},
    onNeedToRequestPermissions: @Composable () -> Unit = {},
    onNeedToRequestPermissionsFromAppSettings: @Composable () -> Unit = {}
) {
    when {
        permissionsState.allPermissionsGranted -> {
            onAllPermissionsGranted()
        }

        permissionsState.shouldShowRationale -> {
            onShowPermissionsRationale()
        }

        else -> {
            if (permissionRequestCompleted) {
                onNeedToRequestPermissionsFromAppSettings()
            } else {
                onNeedToRequestPermissions()
            }
        }
    }
}

@Composable
fun GoToAppSettingsDialog(
    permissionTextProvider: PermissionTextProvider,
    onGoToAppSettingsClicked: () -> Unit
) {
    AlertDialog(
        //modifier = modifier,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = {},
        title = { JText(text = permissionTextProvider.title) },
        text = { JText(text = permissionTextProvider.description) },
        confirmButton = {
            JText(
                textId = R.string.str_go_to_settings,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGoToAppSettingsClicked() }
            )
        }
    )
}

@Composable
fun RationaleDialog(
    permissionTextProvider: PermissionTextProvider,
    onRationaleActionClicked: () -> Unit
) {
    AlertDialog(
        //modifier = modifier,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = {},
        title = { JText(text = permissionTextProvider.title) },
        text = { JText(text = permissionTextProvider.description) },
        confirmButton = {
            JText(
                textId = R.string.str_ok,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRationaleActionClicked() }
            )
        }
    )
}


@Composable
fun Toast(text: String) {
    Toast.makeText(LocalContext.current, text, Toast.LENGTH_LONG).show()
}

@Composable
fun Toast(@StringRes textId: Int) {
    val context = LocalContext.current
    Toast.makeText(context, context.getString(textId), Toast.LENGTH_LONG).show()
}

class Companion() {
    companion object {
        const val MIN_PERMISSIONS = 2
    }
}

