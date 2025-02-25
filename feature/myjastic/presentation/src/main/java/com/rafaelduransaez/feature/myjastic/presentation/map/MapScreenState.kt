package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
data class MapScreenState(
    val currentLocation: LatLng,
    val isSelected: Boolean = false,
    val uiSettings: MapUiSettings = MapUiSettings(myLocationButtonEnabled = false),
    val properties: MapProperties = MapProperties(),
    val cameraPositionState: CameraPositionState,
    val coroutineScope: CoroutineScope
) {
    fun animateCamera(location: LatLng) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(location),
                durationMs = 500
            )
        }
    }

    val enableSaveButton: Boolean
        get() = isSelected
}

@Composable
fun rememberJasticMapUiState(
    currentLocation: LatLng,
    isSelected: Boolean
): MapScreenState {
    val coroutineScope = rememberCoroutineScope()
    val uiSettings = remember { MapUiSettings(myLocationButtonEnabled = false) }
    val properties = remember { MapProperties() }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, MapUtils.DEFAULT_ZOOM)
    }
    return remember(currentLocation, isSelected) {
        MapScreenState(
            currentLocation = currentLocation,
            isSelected = isSelected,
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState,
            coroutineScope = coroutineScope
        )
    }
}