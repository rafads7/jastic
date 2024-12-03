package com.rafaelduransaez.jastic.ui.screens

import android.Manifest
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.jastic.ui.geofences.GeofenceBroadcastReceiver
import com.rafaelduransaez.jastic.ui.geofences.GeofenceHelper
import com.rafaelduransaez.jastic.ui.geofences.GeofenceHelper.Companion.CUSTOM_INTENT_GEOFENCE
import com.rafaelduransaez.jastic.ui.utils.permissions.PermissionBox
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GeofencingScreen() {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    // Requires at least coarse permission
    PermissionBox(
        permissions = permissions,
        requiredPermissions = listOf(permissions.first()),
    ) {
        // For Android 10 onwards, we need background permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PermissionBox(
                permissions = listOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            ) {
                //GeofencingControls()
                MapScreen()
            }
        } else {
            //GeofencingControls()
            MapScreen()
        }
    }
}

@Composable
private fun GeofencingControls() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val geofenceHelper = remember { GeofenceHelper(context) }
    var geofenceTransitionEventInfo by remember { mutableStateOf(String.empty()) }

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            scope.launch(Dispatchers.IO) {
                geofenceHelper.unregisterGeofence()
            }
        }
    }

    // Register a local broadcast to receive activity transition updates
    GeofenceBroadcastReceiver(systemAction = CUSTOM_INTENT_GEOFENCE) { event ->
        geofenceTransitionEventInfo = event
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GeofenceList(geofenceHelper)
        Button(
            onClick = {
                if (geofenceHelper.geofenceList.isNotEmpty()) {
                    geofenceHelper.registerGeofence()
                } else {
                    Toast.makeText(
                        context,
                        "Please add at least one geofence to monitor",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
        ) {
            Text(text = "Register Geofences")
        }

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    geofenceHelper.unregisterGeofence()
                }
            },
        ) {
            Text(text = "Deregister Geofences")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = geofenceTransitionEventInfo)
    }
}

@Composable
fun GeofenceList(GeofenceHelper: GeofenceHelper) {
    // for geofences
    val checkedGeoFence1 = remember { mutableStateOf(false) }
    val checkedGeoFence2 = remember { mutableStateOf(false) }
    val checkedGeoFence3 = remember { mutableStateOf(false) }

    Text(text = "Available Geofence")
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence1.value,
            onCheckedChange = { checked ->
                if (checked) {
                    GeofenceHelper.addGeofence(
                        "CASA",
                        location = Location("").apply {
                            latitude = 41.375159
                            longitude = 2.147349
                        },
                    )
                } else {
                    GeofenceHelper.removeGeofence("CASA")
                }
                checkedGeoFence1.value = checked
            },
        )
        Text(text = "CASA")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence2.value,
            onCheckedChange = { checked ->
                if (checked) {
                    GeofenceHelper.addGeofence(
                        "eiffel_tower",
                        location = Location("").apply {
                            latitude = 48.85850
                            longitude = 2.29455
                        },
                    )
                } else {
                    GeofenceHelper.removeGeofence("eiffel_tower")
                }
                checkedGeoFence2.value = checked
            },
        )
        Text(text = "Eiffel Tower")
    }
    Row(
        Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checkedGeoFence3.value,
            onCheckedChange = { checked ->
                if (checked) {
                    GeofenceHelper.addGeofence(
                        "vatican_city",
                        location = Location("").apply {
                            latitude = 41.90238
                            longitude = 12.45398
                        },
                    )
                } else {
                    GeofenceHelper.removeGeofence("vatican_city")
                }
                checkedGeoFence3.value = checked
            },
        )
        Text(text = "Vatican City")
    }
}