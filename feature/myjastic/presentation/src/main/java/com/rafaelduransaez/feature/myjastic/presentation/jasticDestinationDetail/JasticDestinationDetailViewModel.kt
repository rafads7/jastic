package com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.android.gms.maps.model.LatLng
import com.rafaelduransaez.core.utils.extensions.empty
import com.rafaelduransaez.feature.myjastic.presentation.navigation.Keys.MAP_LATLNG
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticDestinationDetail
import com.rafaelduransaez.feature.myjastic.presentation.utils.Constants.NULL_ISLAND
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JasticDestinationDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mapper: JasticDestinationDetailMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(JasticDestinationDetailUiState())
    val uiState = _uiState
        .onStart {
            loadJasticDestinationDetail()
        }.catch {
            _uiState.update { it.copy(isLoading = false, error = true) }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = JasticDestinationDetailUiState(),
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT)
        )

    fun onUiEvent(event: JasticDestinationDetailUserEvent) {
        when (event) {
            is JasticDestinationDetailUserEvent.AliasUpdate -> {
                _uiState.update { it.copy(alias = event.alias) }
            }

            is JasticDestinationDetailUserEvent.LocationUpdate -> {
                _uiState.update { it.copy(location = event.location) }
            }

            is JasticDestinationDetailUserEvent.LocationSelected -> {
                handleMapLocation(event.location)
                _uiState.update { it.copy(location = mapper.locationToText(event.location)) }
            }

            JasticDestinationDetailUserEvent.Save -> {
                TODO()
            }
        }
    }

    private fun handleMapLocation(location: LatLng) {

    }

    private fun loadJasticDestinationDetail() {
        val detailId = savedStateHandle.toRoute<JasticDestinationDetail>().jasticDestinationId
        fakeDetailIdHandle(detailId)
    }

    private fun fakeDetailIdHandle(detailId: Int) {
        _uiState.update { it.copy(alias = "Alias $detailId") }
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

data class JasticDestinationDetailUiState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val alias: String = String.empty(),
    val location: String = String.empty(),
) {
    val isSaveEnabled: Boolean
        get() = alias.isNotEmpty() && location.isNotEmpty() && error.not() && isLoading.not()

    fun JasticDestinationDetailUiState.empty() =
        copy(alias = String.empty(), location = String.empty())
}

sealed class JasticDestinationDetailUserEvent {
    data object Save : JasticDestinationDetailUserEvent()
    data class LocationUpdate(val location: String) : JasticDestinationDetailUserEvent()
    data class LocationSelected(val location: LatLng) : JasticDestinationDetailUserEvent()
    data class AliasUpdate(val alias: String) : JasticDestinationDetailUserEvent()
}