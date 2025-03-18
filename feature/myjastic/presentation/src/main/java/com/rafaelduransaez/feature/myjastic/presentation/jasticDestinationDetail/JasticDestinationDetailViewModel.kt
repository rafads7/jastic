package com.rafaelduransaez.feature.myjastic.presentation.jasticDestinationDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.contacts.domain.ContactSelectionError
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.models.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetContactInfoUseCase
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticDestinationDetail
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
class JasticDestinationDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getContactInfoUseCase: GetContactInfoUseCase,
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
            is JasticDestinationDetailUserEvent.AliasUpdate ->
                _uiState.update { it.copy(alias = event.alias) }

            is JasticDestinationDetailUserEvent.LocationSelected ->
                onLocationSelected(event.location)

            is JasticDestinationDetailUserEvent.MessageUpdate ->
                _uiState.update { it.copy(message = event.message) }

            is JasticDestinationDetailUserEvent.ContactSelected ->
                onContactSelected(event.contactIdentifier)

            JasticDestinationDetailUserEvent.Save -> Unit
            JasticDestinationDetailUserEvent.SaveAndGo -> Unit
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

    private fun loadJasticDestinationDetail() {
        val detailId = savedStateHandle.toRoute<JasticDestinationDetail>().jasticDestinationId
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

data class JasticDestinationDetailUiState(
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

sealed interface JasticDestinationDetailUserEvent {
    data object Save : JasticDestinationDetailUserEvent
    data object SaveAndGo : JasticDestinationDetailUserEvent
    data class LocationSelected(val location: GeofenceLocation) :
        JasticDestinationDetailUserEvent

    data class AliasUpdate(val alias: String) : JasticDestinationDetailUserEvent
    data class MessageUpdate(val message: String) : JasticDestinationDetailUserEvent
    data class ContactSelected(val contactIdentifier: String) : JasticDestinationDetailUserEvent
}