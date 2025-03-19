package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPoint
import dagger.hilt.android.lifecycle.HiltViewModel
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
        .onStart { loadMyJasticPoints() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT),
            initialValue = MyJasticUiState.Loading
        )

    private fun loadMyJasticPoints() {
        viewModelScope.launch {
            _uiState.update { MyJasticUiState.ShowMyJasticPoints(mockList) }
        }
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

val mockList = buildList {
    repeat(20) { index ->
        add(
            JasticPoint(
                id = index,
                alias = "Alias $index"
            )
        )
    }
}

sealed class MyJasticUiState {
    data object Loading : MyJasticUiState()
    data class ShowMyJasticPoints(val jasticPoints: List<JasticPoint>) : MyJasticUiState()
}
