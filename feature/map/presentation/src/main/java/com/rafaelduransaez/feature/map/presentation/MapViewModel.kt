package com.rafaelduransaez.feature.map.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.core.geofencing.domain.sources.GeofenceHelper
import com.rafaelduransaez.core.navigation.NavigationGraphs
import com.rafaelduransaez.feature.map.domain.usecase.FetchAddressFromLocationUseCase
import com.rafaelduransaez.feature.map.domain.usecase.FetchCurrentLocationUseCase
import com.rafaelduransaez.feature.map.presentation.utils.toGeofence
import com.rafaelduransaez.feature.map.presentation.utils.toGeofenceLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val fetchAddressFromLocationUseCase: FetchAddressFromLocationUseCase,
    private val geofenceHelper: GeofenceHelper
) : ViewModel() {

    private val detailId = savedStateHandle.get<Int>("id")

    private val _uiState: MutableStateFlow<MapUiState> =
        MutableStateFlow(MapUiState(isLoading = true))
    val uiState = _uiState
        .onStart { setInitialLocationToShow() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = MapUiState(isLoading = true)
        )

    internal fun onUiEvent(event: MapUiEvent) {
        when (event) {
            is MapUiEvent.OnGeofenceRadiusChanged -> updateGeofenceRadius(event.radius)
            is MapUiEvent.MapLocationSelected -> fetchAddressFromLocation(
                event.location,
                event.radius
            )

            MapUiEvent.Cancel -> cancelGeofencing()
            MapUiEvent.LocationFetchError -> fetchCurrentLocation()
        }
    }

    private fun setInitialLocationToShow() {
        fetchCurrentLocation()

        val mapData = savedStateHandle.toRoute<NavigationGraphs.MapGraph>()

        if (mapData.isNotEmpty()) {
            setSavedLocation(mapData)
        }

        /*
        val mapData = savedStateHandle.get<MapNavData.Location>(KEY_DATA)
        if (mapData is MapNavData.Location)
            setSavedLocation(mapData)

         */
    }


    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            if (!_uiState.value.isLoading)
                _uiState.update { it.copy(isLoading = true, error = false) }

            fetchCurrentLocationUseCase()
                .catch { _uiState.update { it.copy(error = true) } }
                .collect { location ->
                    location?.let {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentLocation = location,
                            )
                        }
                    } ?: run {
                        _uiState.update { it.copy(isLoading = false, error = true) }
                    }
                }
        }
    }

    private fun fetchAddressFromLocation(latLng: LatLng, radius: Float) {
        val location = latLng.toGeofenceLocation()

        viewModelScope.launch {
            fetchAddressFromLocationUseCase(location).fold(
                onSuccess = { address ->
                    _uiState.update {
                        it.copy(
                            isLocationSelected = true,
                            mapLocation = location.copy(
                                address = address,
                                radiusInMeters = radius
                            )
                        )
                    }
                    //SnackbarHandler.sendEvent(SnackbarEvent(message = address))
                },
                onFailure = { _uiState.update { it.copy(error = true) } }
            )
            withContext(Dispatchers.IO) {
                geofenceHelper.unregisterGeofence()

                val geofence = _uiState.value.mapLocation.toGeofence()
                geofenceHelper.addGeofence(geofence)
            }
        }
    }

    //private fun setSavedLocation(mapData: MapNavData.Location) {
    private fun setSavedLocation(mapData: NavigationGraphs.MapGraph) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isLocationSelected = true,
                mapLocation = GeofenceLocation(
                    latitude = mapData.latitude,
                    longitude = mapData.longitude,
                    radiusInMeters = mapData.radiusInMeters
                )
            )
        }
    }

    private fun updateGeofenceRadius(radius: Float) {
        _uiState.update { it.copy(mapLocation = it.mapLocation.copy(radiusInMeters = radius)) }
    }

    private fun cancelGeofencing() {
        viewModelScope.launch(Dispatchers.IO) {
            geofenceHelper.unregisterGeofence()
        }
    }

    companion object {
        const val RADIUS_MIN_VALUE = 100f
        const val RADIUS_MAX_VALUE = 1000f
        const val CACHE_TIMEOUT = 5000L
    }
}

data class MapUiState(
    val isLoading: Boolean = false,
    val isLocationSelected: Boolean = false,
    val mapLocation: GeofenceLocation = GeofenceLocation(),
    val currentLocation: GeofenceLocation = GeofenceLocation(),
    val error: Boolean = false
)


sealed class MapUiEvent {
    data class MapLocationSelected(val location: LatLng, val radius: Float) : MapUiEvent()
    data class OnGeofenceRadiusChanged(val radius: Float) : MapUiEvent()
    data object LocationFetchError : MapUiEvent()
    data object Cancel : MapUiEvent()
}