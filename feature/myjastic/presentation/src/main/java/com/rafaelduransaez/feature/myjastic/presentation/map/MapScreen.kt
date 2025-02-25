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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
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
import com.rafaelduransaez.feature.myjastic.presentation.utils.toLatLng
import com.rafaelduransaez.feature.myjastic.presentation.utils.toMap

@Composable
internal fun MapScreen(
    uiState: MapUiState,
    onUiEvent: (MapUiEvent) -> Unit,
    onRouteTo: NavRouteTo,
) {
    val mapState = rememberJasticMapUiState(
        currentLocation = uiState.mapLocation.toLatLng(),
        isSelected = uiState.isLocationSelected
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
    ) {
        when {
            uiState.error ->
                JMapError(modifier = Modifier.align(Alignment.Center)) {
                    onUiEvent(LocationFetchError)
                }

            uiState.isLoading ->
                JasticProgressIndicator()

            else ->
                JasticMap(
                    onUiEvent = onUiEvent,
                    onRouteTo = onRouteTo,
                    mapState = mapState,
                    onSave = { onRouteTo(Back, uiState.mapLocation.toMap()) },
                )

        }
    }
}

@Composable
internal fun JasticMap(
    mapState: MapScreenState,
    onUiEvent: (MapUiEvent) -> Unit = {},
    onRouteTo: NavRouteTo,
    onSave: () -> Unit
) {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = mapState.cameraPositionState,
                properties = mapState.properties,
                uiSettings = mapState.uiSettings,
                onMapLongClick = {
                    onUiEvent(MapLocationSelected(it))
                }
            ) {
                LaunchedEffect(mapState.currentLocation) {
                    mapState.animateCamera(mapState.currentLocation)
                }
                if (mapState.isSelected) {
                    MapGeofence(mapState.currentLocation)
                }
            }

            ScaleBar(
                modifier = Modifier
                    .padding(top = JasticTheme.size.extraSmall, end = JasticTheme.size.large)
                    .align(Alignment.TopStart),
                cameraPositionState = mapState.cameraPositionState
            )
        }

        ControlButtons(
            onCancel = { onRouteTo(Back) },
            onSave = onSave,
            enableSaveButton = mapState.enableSaveButton
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
            textId = R.string.str_feature_myjastic_retry
        ) { onRetry() }
    }
}

object MapUtils {
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_RADIUS = 100.0
    const val MEDIUM_RADIUS = 150.0
    const val BIG_RADIUS = 200.0
}