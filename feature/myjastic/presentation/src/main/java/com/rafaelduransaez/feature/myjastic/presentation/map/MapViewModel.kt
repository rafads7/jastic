package com.rafaelduransaez.feature.myjastic.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.LocationHelper
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticViewModel.Companion.CACHE_TIMEOUT
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
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState = _uiState
        .onStart { fetchCurrentLocation() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = MapUiState.Loading
        )

    internal fun fetchCurrentLocation() {
        viewModelScope.launch {
            locationHelper.requestCurrentLocation()
                .onStart { _uiState.update { MapUiState.Loading } }
                .catch { _uiState.update { MapUiState.Error } }
                .collect { location ->
                    location?.let {
                        _uiState.update { MapUiState.ShowLocation(location) }
                    } ?: run {
                        _uiState.update { MapUiState.Error }
                    }
                }
        }
    }

    sealed class MapUiState {
        data object Loading : MapUiState()
        data object Error : MapUiState()
        data class ShowLocation(val currentLocation: LatLng) : MapUiState()
    }
}