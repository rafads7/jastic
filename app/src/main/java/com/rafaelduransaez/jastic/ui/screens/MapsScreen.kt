package com.rafaelduransaez.jastic.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.Flow

@Composable
fun MapScreen(
    /*      paddingValues: PaddingValues,
            viewState: MountainsScreenViewState.MountainList,
            eventFlow: Flow<MountainsScreenEvent>,
            selectedMarkerType: MarkerType,*/
    onMapLongClick: (LatLng) -> Unit = {},
    jasticPointsList: List<LatLng> = emptyList()
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val randomLocation = LatLng(41.375159, 2.147349)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(randomLocation, 15f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            onMapLoaded = { isMapLoaded = true },
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapLongClick = onMapLongClick
        ) {
            jasticPointsList.forEach { location ->
                Circle(
                    center = location,
                    radius = 100.0,
                    fillColor = Color.Red.copy(0.5f)
                )
            }
        }

    }
}

