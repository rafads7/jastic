package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.components.jSnackbar.SnackbarEvent
import com.rafaelduransaez.core.components.jSnackbar.SnackbarHandler
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.usecase.FetchAddressFromLocationUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.FetchCurrentLocationUseCase
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel.Companion.CACHE_TIMEOUT
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mapper: MapMapper,
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val fetchAddressFromLocationUseCase: FetchAddressFromLocationUseCase
) : ViewModel() {

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
            MapUiEvent.LocationFetchError -> fetchCurrentLocation()
            is MapUiEvent.MapLocationSelected -> fetchAddressFromLocation(event.location)
        }
    }

    private fun setInitialLocationToShow() {
        val mapData = savedStateHandle.toRoute<MyJasticRoutes.Map>()
        if (mapData.isEmpty()) {
            fetchCurrentLocation()
        } else {
            val location = GeofenceLocation(
                latitude = mapData.latitude,
                longitude = mapData.longitude
            )
            _uiState.update {
                it.copy(
                    isLoading = false,
                    mapLocation = location,
                    isLocationSelected = true,
                )
            }
        }
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
                            it.copy(isLoading = false, mapLocation = location)
                        }
                    } ?: run {
                        _uiState.update { it.copy(isLoading = false, error = true) }
                    }
                }
        }
    }

    private fun fetchAddressFromLocation(latLng: LatLng) {
        val location = mapper.latLngToGeofenceLocation(latLng)

        viewModelScope.launch {
            fetchAddressFromLocationUseCase(location).fold(
                onSuccess = { address ->
                    _uiState.update {
                        it.copy(
                            isLocationSelected = true,
                            mapLocation = location.copy(address = address)
                        )
                    }
                    SnackbarHandler.sendEvent(SnackbarEvent(message = address))
                },
                onFailure = { _uiState.update { it.copy(error = true) } }
            )
        }
    }
}

data class MapUiState(
    val isLoading: Boolean = false,
    val isLocationSelected: Boolean = false,
    val mapLocation: GeofenceLocation = GeofenceLocation(),
    val error: Boolean = false
)


sealed class MapUiEvent {
    data class MapLocationSelected(val location: LatLng) : MapUiEvent()
    data object LocationFetchError : MapUiEvent()
}