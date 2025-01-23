package com.rafaelduransaez.jastic.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rafaelduransaez.domain.components.common.empty
import com.rafaelduransaez.domain.components.common.zero
import dagger.hilt.android.lifecycle.HiltViewModel


//@HiltViewModel
class NewJasticPointViewModel : ViewModel() {


}

sealed class NewJasticPointUiState {
    data object Loading : NewJasticPointUiState()
    data class Error(val message: String) : NewJasticPointUiState()
    data class ShowFields(val fieldsState: JasticPointInfoState) : NewJasticPointUiState()
}
    

class JasticPointInfoState {
    var name: String by mutableStateOf(String.empty())
    var surname: String by mutableStateOf(String.empty())
    var age: Int by mutableIntStateOf(Int.zero())
}