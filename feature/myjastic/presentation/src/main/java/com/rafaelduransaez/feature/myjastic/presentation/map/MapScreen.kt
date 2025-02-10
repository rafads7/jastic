package com.rafaelduransaez.feature.myjastic.presentation.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.rafaelduransaez.feature.myjastic.presentation.utils.Constants.NULL_ISLAND
import com.rafaelduransaez.core.permissions.PermissionsRequester
import com.rafaelduransaez.feature.myjastic.presentation.R

val LatLngSaver = Saver<LatLng, List<Double>>(
    save = { listOf(it.latitude, it.longitude) },
    restore = { LatLng(it[0], it[1]) }
)

val mandatoryPermissions = listOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

@Composable
fun MapScreen(
    onSave: (LatLng) -> Unit = {},
    onCancel: () -> Unit = {},
    viewModel: MapViewModel = hiltViewModel()
) {
    var showMap by remember { mutableStateOf(false) }

    PermissionsRequester(mandatoryPermissions) {
        showMap = true
    }

    if (showMap) {
        val locationState by viewModel.uiState.collectAsStateWithLifecycle()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 8.dp)
        ) {
            when (val state = locationState) {
                is MapViewModel.MapUiState.Loading -> {
                    JasticProgressIndicator()
                }

                is MapViewModel.MapUiState.Error -> {
                    JMapError(modifier = Modifier.align(Alignment.Center)) {
                        viewModel.fetchCurrentLocation()
                    }
                }

                is MapViewModel.MapUiState.ShowLocation -> {
                    JMap(
                        onSave = onSave,
                        onCancel = onCancel,
                        currentLocation = state.currentLocation
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.JMap(
    onSave: (LatLng) -> Unit,
    onCancel: () -> Unit,
    currentLocation: LatLng
) {
    var isMapLoaded by remember { mutableStateOf(false) }

    val uiSettings by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = true)) }
    val properties by remember { mutableStateOf(MapProperties()) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, MapUtils.DEFAULT_ZOOM)
        //position = CameraPosition.fromLatLngZoom(LatLng(41.375159, 2.147349), MapUtils.DEFAULT_ZOOM)
    }
    var currentGeofenceLocation by rememberSaveable(stateSaver = LatLngSaver) {
        mutableStateOf(NULL_ISLAND)
    }

    //val bcn = LatLng(41.375159, 2.147349)
    //val current = LatLng(37.4219983, -122.084)
    //val atitlan = LatLng(14.713127, -91.203690)

    val enableSaveButton by remember { derivedStateOf { currentGeofenceLocation != NULL_ISLAND } }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMapLoaded = { isMapLoaded = true },
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapLongClick = {
            currentGeofenceLocation = it
        }
    ) {
        if (currentGeofenceLocation != NULL_ISLAND)
            MapsGeofence(currentGeofenceLocation)
    }

    ScaleBar(
        modifier = Modifier
            .padding(top = JasticTheme.size.extraSmall, end = JasticTheme.size.large)
            .align(Alignment.TopStart),
        cameraPositionState = cameraPositionState
    )

    ControlButtons(
        onCancel = onCancel,
        enableSaveButton = enableSaveButton,
        onSave = onSave,
        currentGeofenceLocation = currentGeofenceLocation
    )

}

@Composable
private fun BoxScope.ControlButtons(
    onCancel: () -> Unit,
    enableSaveButton: Boolean,
    onSave: (LatLng) -> Unit,
    currentGeofenceLocation: LatLng
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        JCancelButton(
            modifier = Modifier.weight(1f)
        ) { onCancel() }

        JSaveButton(
            modifier = Modifier.weight(1f),
            enabled = enableSaveButton
        ) { onSave(currentGeofenceLocation) }
    }
}

@Composable
fun MapsGeofence(location: LatLng) {
    Circle(
        center = location,
        radius = MapUtils.MEDIUM_RADIUS,
        fillColor = JasticTheme.colorScheme.error.copy(0.5f),
        strokeColor = JasticTheme.colorScheme.onErrorContainer
    )
}

@Composable
fun JMapError(modifier: Modifier = Modifier, onRetry: () -> Unit) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.4f) .aspectRatio(1f),
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
