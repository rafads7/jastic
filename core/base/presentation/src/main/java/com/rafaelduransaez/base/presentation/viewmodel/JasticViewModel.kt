package com.rafaelduransaez.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class JasticViewModel</*UiState, */NavState> : ViewModel() {

/*    protected val _uiState = MutableStateFlow(stateData.initialValue)

    val uiState: StateFlow<UiState> = _uiState
        .onStart { emitAll(stateData.onStart(_uiState)) }
        .stateIn(
            scope = stateData.scope,
            initialValue = stateData.initialValue,
            started = stateData.started
        )*/

    private val _navState = Channel<NavState>()
    val navState = _navState.receiveAsFlow()

    open fun navigateTo(navDestination: NavState) {
        viewModelScope.launch {
            _navState.send(navDestination)
        }
    }

/*    data class UiStateData<UiState>(
        val initialValue: UiState,
        val scope: CoroutineScope,
        val started: SharingStarted = SharingStarted.WhileSubscribed(5000),
        val onStart: suspend Flow<UiState>.() -> Flow<UiState> = { this }
    )*/
}
