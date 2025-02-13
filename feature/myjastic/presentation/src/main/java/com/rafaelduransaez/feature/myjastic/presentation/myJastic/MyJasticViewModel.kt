package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.feature.myjastic.domain.model.JasticDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyJasticViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MyJasticUiState>(MyJasticUiState.Loading)
    val uiState = _uiState
        .onStart { loadMyJasticDestinations() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = MyJasticUiState.Loading
        )

    private fun loadMyJasticDestinations() {
        viewModelScope.launch {
            delay(CACHE_TIMEOUT)
            _uiState.update { MyJasticUiState.ShowMyJasticDestinations(fakeList()) }
            //_uiState.update { MyJasticUiState.ShowMyJasticDestinations(emptyList()) }
        }
    }

    private fun fakeList() = buildList {
        repeat(20) { index ->
            add(
                JasticDestination(
                    id = index,
                    title = "Title ${index + 1}",
                    description = "Description ${index + 1}"
                )
            )
        }
    }

    companion object {
        const val CACHE_TIMEOUT = 2000L
    }
}


sealed class MyJasticUiState {
    data object Loading : MyJasticUiState()
    data class ShowMyJasticDestinations(val jasticDestinations: List<JasticDestination>) : MyJasticUiState()
}
