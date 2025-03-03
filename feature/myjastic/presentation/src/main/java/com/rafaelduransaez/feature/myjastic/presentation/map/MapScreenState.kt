package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
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
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreenState.Companion.DEFAULT_ZOOM
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreenState.Companion.SLIDER_INITIAL_VALUE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Stable
data class MapScreenState(
    val currentLocation: LatLng,
    val isSelected: Boolean = false,
    val uiSettings: MapUiSettings = MapUiSettings(myLocationButtonEnabled = false),
    val properties: MapProperties = MapProperties(),
    val cameraPositionState: CameraPositionState,
    val sliderState: MutableState<Float>,
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

    companion object {
        const val SLIDER_MIN_VALUE = 100f
        const val SLIDER_MAX_VALUE = 1000f
        const val SLIDER_INITIAL_VALUE = 200f
        const val SLIDER_STEPS = 8
        const val DEFAULT_ZOOM = 15f
    }
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
        position = CameraPosition.fromLatLngZoom(currentLocation, DEFAULT_ZOOM)
    }
    val sliderState = remember { mutableFloatStateOf(SLIDER_INITIAL_VALUE) }

    return remember(currentLocation, isSelected) {
        MapScreenState(
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
