package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.GeocoderHelper
import com.rafaelduransaez.core.LocationHelper
import com.rafaelduransaez.core.components.jSnackbar.SnackbarEvent
import com.rafaelduransaez.core.components.jSnackbar.SnackbarHandler
import com.rafaelduransaez.core.utils.extensions.empty
import com.rafaelduransaez.feature.myjastic.domain.model.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel.Companion.CACHE_TIMEOUT
import com.rafaelduransaez.feature.myjastic.presentation.utils.Constants.NULL_ISLAND
import com.rafaelduransaez.feature.myjastic.presentation.utils.toGeofenceLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationHelper: LocationHelper,
    private val geocoderHelper: GeocoderHelper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DataMapUiState> = MutableStateFlow(DataMapUiState())
    val uiState = _uiState
        .onStart { fetchCurrentLocation() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = DataMapUiState()
        )

    internal fun onUiEvent(event: MapUiEvent) {
        when (event) {
            MapUiEvent.LocationFetchError -> fetchCurrentLocation()
            is MapUiEvent.MapLocationSelected -> fetchAddressFromLocation(event.location)
        }
    }

    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            locationHelper.requestCurrentLocation()
                .catch { _uiState.update { it.copy(error = true) } }
                .collect { location ->
                    location?.let {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = false,
                                currentLocation = location.toGeofenceLocation()
                            )
                        }
                    } ?: run {
                        _uiState.update { it.copy(error = true) }
                    }
                }
        }
    }

    private fun fetchAddressFromLocation(location: LatLng) {
        geocoderHelper.getAddressFromLatLng(
            location = location,
            onResult = { address ->
                _uiState.update {
                    it.copy(
                        geofenceLocation = GeofenceLocation(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            address = address
                        )
                    )
                }
                viewModelScope.launch {
                    SnackbarHandler.sendEvent(SnackbarEvent(message = address))
                }
            },
            onError = {
                _uiState.update { it.copy(error = true) }
            }
        )
    }
    /*    private fun fetchCurrentLocation() {
            viewModelScope.launch {
                locationHelper.requestCurrentLocation()
                    .catch { _uiState.update { MapUiState.Error } }
                    .collect { location ->
                        location?.let {
                            _uiState.update { MapUiState.ShowLocation(currentLocation = location) }
                        } ?: run {
                            _uiState.update { MapUiState.Error }
                        }
                    }
            }
        }

        private fun fetchAddressFromLocation(location: LatLng) {
            geocoderHelper.getAddressFromLatLng(
                location = location,
                onResult = { address ->
                    _uiState.update {
                        MapUiState.ShowLocation(
                            geofenceLocation = location,
                            address = address
                        )
                    }
                    viewModelScope.launch {
                        SnackbarHandler.sendEvent(SnackbarEvent(message = address))
                    }
                },
                onError = {
                    _uiState.update { MapUiState.Error }
                }
            )
        }*/


}

sealed class MapUiState() {
    data object Loading : MapUiState()
    data object Error : MapUiState()
    data class ShowMap(
        val currentLocation: LatLng = NULL_ISLAND,
        val geofenceLocation: LatLng? = null,
        val address: String = String.empty()
    ) : MapUiState()
}

data class DataMapUiState(
    val isLoading: Boolean = false,
    val currentLocation: GeofenceLocation = GeofenceLocation(),
    val geofenceLocation: GeofenceLocation? = null,
    val error: Boolean = false
)


sealed class MapUiEvent {
    data class MapLocationSelected(val location: LatLng) : MapUiEvent()
    data object LocationFetchError : MapUiEvent()
}