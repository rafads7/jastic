package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetJasticPointsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyJasticViewModel @Inject constructor(
    private val getJasticPointsUseCase: GetJasticPointsListUseCase
) : ViewModel() {

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
            _uiState.update { MyJasticUiState.Loading }
            getJasticPointsUseCase().collect {
                it.fold(
                    onSuccess = ::onGetMyJasticPointsSuccess,
                    onFailure = ::onGetMyJasticPointsError
                )
            }
        }
    }

    private fun onGetMyJasticPointsSuccess(jasticPoints: List<JasticPointListItemUI>) {
        _uiState.update { MyJasticUiState.ShowMyJasticPoints(jasticPoints) }
    }

    private fun onGetMyJasticPointsError(databaseError: DatabaseError) {
        _uiState.update { MyJasticUiState.Error }
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

sealed class MyJasticUiState {
    data object Error: MyJasticUiState()
    data object Loading : MyJasticUiState()
    data class ShowMyJasticPoints(val jasticPoints: List<JasticPointListItemUI>) : MyJasticUiState()
}
