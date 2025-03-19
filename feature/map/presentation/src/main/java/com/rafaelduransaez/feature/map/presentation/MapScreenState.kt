package com.rafaelduransaez.feature.map.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.rafaelduransaez.feature.map.presentation.MapScreenState.Companion.DEFAULT_ZOOM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Stable
data class MapScreenState(
    val mapLocation: LatLng,
    val currentLocation: LatLng,
    val isSelected: Boolean = false,
    val uiSettings: MapUiSettings = MapUiSettings(myLocationButtonEnabled = true),
    val properties: MapProperties = MapProperties(),
    val cameraPositionState: CameraPositionState,
    val sliderState: MutableState<Float>,
    val coroutineScope: CoroutineScope,
) {
    fun animateCamera(location: LatLng) {
        coroutineScope.launch {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLng(location),
                durationMs = 500
            )
        }
    }

/*    fun toggleCamera() {
        if (!isSelected)
            animateCamera(currentLocation)
        else if(cameraPositionState.position.target.sphericalDistance())
    }*/

    val enableSaveButton: Boolean
        get() = isSelected

    companion object {
        const val SLIDER_STEPS = 8
        const val DEFAULT_ZOOM = 15f
    }
}

@Composable
fun rememberJasticMapUiState(
    mapLocation: LatLng,
    currentLocation: LatLng,
    radiusInMeters: Float,
    isSelected: Boolean,
): MapScreenState {
    val coroutineScope = rememberCoroutineScope()
    val uiSettings = remember { MapUiSettings(myLocationButtonEnabled = false) }
    val properties = remember { MapProperties() }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mapLocation, DEFAULT_ZOOM)
    }
    val sliderState = rememberSaveable(radiusInMeters) { mutableFloatStateOf(radiusInMeters) }

    return remember(mapLocation, currentLocation, isSelected) {
        MapScreenState(
            mapLocation = mapLocation,
            currentLocation = currentLocation,
            isSelected = isSelected,
            uiSettings = uiSettings,
            properties = properties,
            cameraPositionState = cameraPositionState,
            sliderState = sliderState,
            coroutineScope = coroutineScope
        )
    }
}
