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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.widgets.ScaleBar
import com.rafaelduransaez.core.components.common.JasticProgressIndicator
import com.rafaelduransaez.core.components.jButton.JButton
import com.rafaelduransaez.core.components.jButton.JCancelButton
import com.rafaelduransaez.core.components.jButton.JSaveButton
import com.rafaelduransaez.core.components.jText.JText
import com.rafaelduransaez.core.designsystem.JasticTheme
import com.rafaelduransaez.core.navigation.Back
import com.rafaelduransaez.core.navigation.NavRouteTo
import com.rafaelduransaez.core.navigation.invoke
import com.rafaelduransaez.feature.myjastic.presentation.R
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreenState.Companion.SLIDER_MAX_VALUE
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreenState.Companion.SLIDER_MIN_VALUE
import com.rafaelduransaez.feature.myjastic.presentation.map.MapScreenState.Companion.SLIDER_STEPS
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

        JasticMapSlider(
            mapState = mapState,
            onSliderValueChange = { mapState.sliderState.value = it }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            GoogleMap(
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
                    MapGeofence(mapState)
                }
            }

            ScaleBar(
                modifier = Modifier
                    .padding(top = JasticTheme.size.extraSmall, end = JasticTheme.size.large)
                    .align(Alignment.TopStart),
                cameraPositionState = mapState.cameraPositionState
            )

            JText(
                modifier = Modifier
                    .padding(all = JasticTheme.size.small)
                    .align(Alignment.TopEnd),
                text = stringResource(R.string.str_feature_myjastic_geofence_radius, mapState.sliderState.value.toInt()),
                style = JasticTheme.typography.labelLight
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
fun JasticMapSlider(mapState: MapScreenState, onSliderValueChange: (Float) -> Unit) {

    Slider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = JasticTheme.size.small),
        value = mapState.sliderState.value,
        onValueChange = onSliderValueChange,
        valueRange = SLIDER_MIN_VALUE..SLIDER_MAX_VALUE,
        steps = SLIDER_STEPS,
        colors = SliderDefaults.colors(
            thumbColor = JasticTheme.colorScheme.secondary,
            activeTrackColor = JasticTheme.colorScheme.secondary,
            inactiveTrackColor = JasticTheme.colorScheme.secondaryContainer,
        ),
        enabled = mapState.isSelected
    )
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
private fun MapGeofence(mapState: MapScreenState) {
    Circle(
        center = mapState.currentLocation,
        radius = mapState.sliderState.value.toDouble(),
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