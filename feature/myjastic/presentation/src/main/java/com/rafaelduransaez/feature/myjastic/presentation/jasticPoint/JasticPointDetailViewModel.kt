package com.rafaelduransaez.feature.myjastic.presentation.jasticPoint

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetContactInfoUseCase
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticPointDetail
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
class JasticPointDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getContactInfoUseCase: GetContactInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(JasticPointDetailUiState())
    val uiState = _uiState
        .onStart {
            loadJasticPointDetail()
        }.catch {
            _uiState.update { it.copy(isLoading = false, error = true) }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = JasticPointDetailUiState(),
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT)
        )

    fun onUiEvent(event: JasticPointDetailUserEvent) {
        when (event) {
            is JasticPointDetailUserEvent.AliasUpdate ->
                _uiState.update { it.copy(alias = event.alias) }

            is JasticPointDetailUserEvent.LocationSelected ->
                onLocationSelected(event.location)

            is JasticPointDetailUserEvent.MessageUpdate ->
                _uiState.update { it.copy(message = event.message) }

            is JasticPointDetailUserEvent.ContactSelected ->
                onContactSelected(event.contactIdentifier)

            JasticPointDetailUserEvent.Save -> Unit
            JasticPointDetailUserEvent.SaveAndGo -> Unit
            is JasticPointDetailUserEvent.SaveLocation -> Unit
        }
    }

    private fun onLocationSelected(location: GeofenceLocation) {
        val address = location.address.ifEmpty { _uiState.value.location.address }
        val updatedLocation = location.copy(address = address)
        _uiState.update { it.copy(location = updatedLocation) }
    }

    private fun onContactSelected(contactIdentifier: String) {
        viewModelScope.launch {
            getContactInfoUseCase(contactIdentifier).fold(
                onSuccess = { contact -> _uiState.update { it.copy(contact = contact) } },
                onFailure = { failure ->
                    _uiState.update { it.copy(error = true) }
                    /*                            when (failure) {
                                            ContactSelectionError.FieldNotFound -> TODO()
                                            ContactSelectionError.Unknown -> _uiState.update { it.copy(error = true) }
                                        }*/
                }
            )
        }
    }

    private fun loadJasticPointDetail() {
        val detailId = savedStateHandle.toRoute<JasticPointDetail>().jasticPointId
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

data class JasticPointDetailUiState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val alias: String = String.empty(),
    val location: GeofenceLocation = GeofenceLocation(),
    val message: String = String.empty(),
    val contact: Contact = Contact()
) {
    val isSaveEnabled: Boolean
        get() = alias.isNotEmpty() && location.address.isNotEmpty() && error.not() && isLoading.not()
}

sealed interface JasticPointDetailUserEvent {
    data object Save : JasticPointDetailUserEvent
    data object SaveAndGo : JasticPointDetailUserEvent
    data class LocationSelected(val location: GeofenceLocation) :
        JasticPointDetailUserEvent

    data class AliasUpdate(val alias: String) : JasticPointDetailUserEvent
    data class MessageUpdate(val message: String) : JasticPointDetailUserEvent
    data class ContactSelected(val contactIdentifier: String) : JasticPointDetailUserEvent
    data class SaveLocation(val save: Boolean) : JasticPointDetailUserEvent
}