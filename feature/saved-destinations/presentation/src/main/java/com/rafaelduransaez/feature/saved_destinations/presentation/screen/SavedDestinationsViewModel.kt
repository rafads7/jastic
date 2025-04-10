package com.rafaelduransaez.feature.saved_destinations.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI
import com.rafaelduransaez.feature.saved_destinations.domain.usecases.GetSavedDestinationsUseCase
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsNavState.*
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsUiEvent.OnDestinationClick
import com.rafaelduransaez.feature.saved_destinations.presentation.screen.SavedDestinationsUiEvent.OnEditDestinationClick
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedDestinationsViewModel @Inject constructor(
    private val getSavedDestinationsUseCase: GetSavedDestinationsUseCase,
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SavedDestinationsUiState>(SavedDestinationsUiState.Loading)
    val uiState = _uiState
        .onStart { loadSavedDestinations() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = SavedDestinationsUiState.Loading
        )

    private val _navState = Channel<SavedDestinationsNavState>()
    val navState = _navState.receiveAsFlow()

    internal fun onUiEvent(event: SavedDestinationsUiEvent) {
        viewModelScope.launch {
            when (event) {
                is OnDestinationClick -> _navState.send(ToNewJasticPoint(event.destinationId))
                is OnEditDestinationClick -> _navState.send(ToSavedDestinationsEdit(event.destinationId))
            }
        }
    }

    private fun loadSavedDestinations() {
        viewModelScope.launch {
            _uiState.update { SavedDestinationsUiState.Loading }
            getSavedDestinationsUseCase().collect { result ->
                result.fold(
                    onSuccess = ::onGetSavedDestinationsSuccess,
                    onFailure = ::onGetSavedDestinationsError
                )
            }
        }
    }

    private fun onGetSavedDestinationsSuccess(savedDestinations: List<DestinationsListItemUI>) {
        _uiState.update { SavedDestinationsUiState.Success(savedDestinations) }
    }

    private fun onGetSavedDestinationsError(error: DatabaseError) {
        _uiState.update { SavedDestinationsUiState.Error }
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }

}

sealed interface SavedDestinationsUiState {
    data object Loading : SavedDestinationsUiState
    data object Error : SavedDestinationsUiState
    data class Success(val savedDestinations: List<DestinationsListItemUI>) :
        SavedDestinationsUiState
}

sealed interface SavedDestinationsUiEvent {
    data class OnDestinationClick(val destinationId: Long) : SavedDestinationsUiEvent
    data class OnEditDestinationClick(val destinationId: Long) : SavedDestinationsUiEvent
}

sealed interface SavedDestinationsNavState {
    data object Idle : SavedDestinationsNavState
    data class ToSavedDestinationsEdit(val destinationId: Long) : SavedDestinationsNavState
    data class ToNewJasticPoint(val destinationId: Long) : SavedDestinationsNavState
    data object Back : SavedDestinationsNavState
}
