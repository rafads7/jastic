package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.widgets.ScaleBar
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.jButton.JButton
import com.rafaelduransaez.core.components.jButton.JCancelButton
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.map.MapUiEvent.LocationFetchError
import com.rafaelduransaez.feature.myjastic.presentation.map.MapUiEvent.MapLocationSelected
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.MAP_LATLNG
import com.rafaelduransaez.feature.myjastic.presentation.utils.toLatLng

val LatLngSaver = Saver<LatLng, List<Double>>(
    save = { listOf(it.latitude, it.longitude) },
    restore = { LatLng(it[0], it[1]) }
)

@Composable
internal fun MapScreen(
    uiState: DataMapUiState,
    onUiEvent: (MapUiEvent) -> Unit,
    onRouteTo: NavRouteTo,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        when {
            uiState.error -> JMapError(modifier = Modifier.align(Alignment.Center)) {
                onUiEvent(LocationFetchError)
            }

            uiState.isLoading -> JasticProgressIndicator()
            else -> JasticMap(
                onUiEvent = onUiEvent,
                onRouteTo = onRouteTo,
                currentLocation = uiState.currentLocation.toLatLng(),
                geofenceLocation = uiState.geofenceLocation?.toLatLng(),
                onSave = { onRouteTo(Back, mapOf(MAP_LATLNG to uiState.geofenceLocation!!)) },
            )
        }

        /*when (uiState) {
            MapUiState.Loading -> JasticProgressIndicator()
            is MapUiState.Error -> {
                JMapError(modifier = Modifier.align(Alignment.Center)) {
                    onUiEvent(LocationFetchError)
                }
            }

            is MapUiState.ShowLocation -> {
                JasticMap(
                    onUiEvent = onUiEvent,
                    onRouteTo = onRouteTo,
                    currentLocation = uiState.currentLocation,
                    geofenceLocation = uiState.geofenceLocation
                )
            }
        }*/
    }
}

@Composable
internal fun JasticMap(
    currentLocation: LatLng,
    geofenceLocation: LatLng?,
    onUiEvent: (MapUiEvent) -> Unit = {},
    onRouteTo: NavRouteTo,
    onSave: () -> Unit
) {
    val uiSettings = remember { MapUiSettings(myLocationButtonEnabled = false) }
    val properties = remember { MapProperties() }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            geofenceLocation ?: currentLocation, MapUtils.DEFAULT_ZOOM
        )
    }

    val enableSaveButton by remember(geofenceLocation) { derivedStateOf { geofenceLocation != null } }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings,
                onMapLongClick = {
                    onUiEvent(MapLocationSelected(it))
                }
            ) {
                if (geofenceLocation != null) {
                    MapGeofence(geofenceLocation)
                }
            }

            ScaleBar(
                modifier = Modifier
                    .padding(top = JasticTheme.size.extraSmall, end = JasticTheme.size.large)
                    .align(Alignment.TopStart),
                cameraPositionState = cameraPositionState
            )
        }

        ControlButtons(
            onCancel = { onRouteTo(Back) },
            onSave = onSave,
            enableSaveButton = enableSaveButton
        )
    }
}

@Composable
private fun ControlButtons(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    enableSaveButton: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        JCancelButton(
            modifier = Modifier.weight(1f)
        ) { onCancel() }

        JSaveButton(
            modifier = Modifier.weight(1f),
            enabled = enableSaveButton
        ) { onSave() }
    }
}

@Composable
private fun MapGeofence(location: LatLng) {
    Circle(
        center = location,
        radius = MapUtils.MEDIUM_RADIUS,
        fillColor = JasticTheme.colorScheme.error.copy(0.5f),
        strokeColor = JasticTheme.colorScheme.onErrorContainer
    )
}

@Composable
private fun JMapError(modifier: Modifier = Modifier, onRetry: () -> Unit) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.img_location_error),
            contentDescription = "My Raster Image",
            contentScale = ContentScale.Fit
        )
        JButton(
            modifier = Modifier.padding(all = JasticTheme.size.small),
            textId = R.string.str_feature_my_jastic_retry
        ) { onRetry() }
    }
}

object MapUtils {
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_RADIUS = 100.0
    const val MEDIUM_RADIUS = 150.0
    const val BIG_RADIUS = 200.0
}
