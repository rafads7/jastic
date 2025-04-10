package com.rafaelduransaez.feature.myjastic.presentation.jasticPoint

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.rafaelduransaez.base.presentation.viewmodel.JasticViewModel
import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.contacts.domain.Contact
import com.rafaelduransaez.core.domain.extensions.empty
import com.rafaelduransaez.core.domain.extensions.isPositive
import com.rafaelduransaez.core.domain.extensions.zero
import com.rafaelduransaez.feature.map.domain.model.GeofenceLocation
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetContactInfoUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetJasticPointUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.SaveJasticPointUseCase
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.DestinationSavingOptions.Idle
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.DestinationSavingOptions.Update
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailNavState.ToDestinationSelectionMap
import com.rafaelduransaez.feature.myjastic.presentation.navigation.MyJasticRoutes.JasticPointDetail
import com.rafaelduransaez.feature.myjastic.presentation.utils.toJasticPointDetailUiState
import com.rafaelduransaez.feature.myjastic.presentation.utils.toJasticPointUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JasticPointDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getContactInfoUseCase: GetContactInfoUseCase,
    private val getJasticPointUseCase: GetJasticPointUseCase,
    private val saveJasticPointUseCase: SaveJasticPointUseCase
) : ViewModel() {//JasticViewModel<JasticPointDetailNavState>() {

    private val _uiState = MutableStateFlow(JasticPointDetailUiState())
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            initialValue = JasticPointDetailUiState(isLoading = true),
            started = SharingStarted.WhileSubscribed(CACHE_TIMEOUT)
        )

    init {
        // .onStart is not used in _uiState Flow because it relaunches when we're back from Map.
        loadJasticPointDetail()
    }

    private val _navState = Channel<JasticPointDetailNavState>()
    val navState = _navState.receiveAsFlow()

    private fun navigateTo(navDestination: JasticPointDetailNavState) {
        viewModelScope.launch {
            _navState.send(navDestination)
        }
    }

    fun onUiEvent(event: JasticPointDetailUserEvent) {
        when (event) {
            is JasticPointDetailUserEvent.AliasUpdate ->
                _uiState.update { it.copy(jasticPointAlias = event.alias) }

            is JasticPointDetailUserEvent.DestinationSelected ->
                onDestinationSelected(event.location)

            is JasticPointDetailUserEvent.MessageUpdate ->
                _uiState.update { it.copy(jasticPointMessage = event.message) }

            is JasticPointDetailUserEvent.ContactSelected ->
                onContactSelected(event.contactIdentifier)

            is JasticPointDetailUserEvent.DestinationAliasUpdate ->
                _uiState.update { it.copy(destinationAlias = event.destinationAlias) }

            JasticPointDetailUserEvent.Save -> onSave()
            JasticPointDetailUserEvent.SaveAndGo -> Unit
            is JasticPointDetailUserEvent.DestinationSavingOptionsChanged ->
                _uiState.update { it.copy(destinationSavingOptions = event.state) }

            JasticPointDetailUserEvent.DestinationIconClicked ->
                navigateTo(ToDestinationSelectionMap(
                    latitude = _uiState.value.geofenceLocation.latitude,
                    longitude = _uiState.value.geofenceLocation.longitude,
                    radiusInMeters = _uiState.value.geofenceLocation.radiusInMeters
                ))
        }
    }

    private fun onDestinationSelected(selectedLocation: GeofenceLocation) {
        val address = selectedLocation.address.ifEmpty { _uiState.value.geofenceLocation.address }
        val updatedLocation = selectedLocation.copy(address = address)
        _uiState.update {
            it.copy(
                geofenceLocation = updatedLocation,
                destinationSavingOptions = Update,
                showDestinationSavingOptions = true
            )
        }
    }

    private fun onContactSelected(contactIdentifier: String) {
        viewModelScope.launch {
            getContactInfoUseCase(contactIdentifier).fold(
                onSuccess = ::onGetContactInfoSuccess,
                onFailure = { _uiState.update { it.copy(error = true) } }
            )
        }
    }

    private fun onGetContactInfoSuccess(contact: Contact) {
        _uiState.update {
            it.copy(
                contactPhoneNumber = contact.phoneNumber,
                contactAlias = contact.name
            )
        }
    }

    private fun onSave() {
        viewModelScope.launch {

            with(_uiState) {

                update { it.copy(isLoading = true) }

                if (value.destinationSavingOptions != Update)
                    update { it.copy(destinationId = Long.zero) }

                saveJasticPointUseCase(value.toJasticPointUI()).fold(
                    onSuccess = ::onJasticPointSaved,
                    onFailure = ::onJasticPointFailedToSaved
                )
            }
        }
    }

    private fun onJasticPointSaved(jasticPointId: Long) {
        _uiState.update { it.copy(isLoading = false) }
        navigateTo(JasticPointDetailNavState.ToMyJasticList)
    }

    private fun onJasticPointFailedToSaved(error: DatabaseError) {
        _uiState.update { it.copy(isLoading = false) }
    }

    private fun loadJasticPointDetail() {
        val id = savedStateHandle.toRoute<JasticPointDetail>().jasticPointId

        if (id.isPositive()) {
            viewModelScope.launch {
                getJasticPointUseCase(id).collect { result ->
                    result.fold(
                        onSuccess = ::onLoadingJasticPointSuccess,
                        onFailure = ::onLoadingJasticPointError
                    )
                }
            }
        }
    }

    private fun onLoadingJasticPointSuccess(jasticPoint: JasticPointUI) {
        _uiState.update {
            jasticPoint.toJasticPointDetailUiState(
                currentState = it.copy(
                    error = false,
                    isLoading = false,
                    destinationSavingOptions = Update
                )
            )
        }
    }

    private fun onLoadingJasticPointError(error: DatabaseError) {
        _uiState.update { it.copy(isLoading = false, error = true) }
    }

    companion object {
        const val CACHE_TIMEOUT = 5000L
    }
}

data class JasticPointDetailUiState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val jasticPointId: Long = Long.zero,
    val jasticPointAlias: String = String.empty,
    val jasticPointMessage: String = String.empty,
    val contactPhoneNumber: String = String.empty,
    val contactAlias: String = String.empty,
    val geofenceLocation: GeofenceLocation = GeofenceLocation(),
    val destinationId: Long = Long.zero,
    val destinationAlias: String = String.empty,
    val destinationSavingOptions: DestinationSavingOptions = Idle,
    val showDestinationSavingOptions: Boolean = false
) {
    val isSaveEnabled: Boolean
        get() = jasticPointAlias.isNotEmpty()
                && jasticPointMessage.isNotEmpty()
                && contactPhoneNumber.isNotEmpty()
                && geofenceLocation.isInitialized
                && error.not()
                && isLoading.not()
}

sealed interface JasticPointDetailUserEvent {
    data object Save : JasticPointDetailUserEvent
    data object SaveAndGo : JasticPointDetailUserEvent
    data class DestinationSelected(val location: GeofenceLocation) : JasticPointDetailUserEvent
    data class AliasUpdate(val alias: String) : JasticPointDetailUserEvent
    data class MessageUpdate(val message: String) : JasticPointDetailUserEvent
    data class ContactSelected(val contactIdentifier: String) : JasticPointDetailUserEvent
    data class DestinationAliasUpdate(val destinationAlias: String) : JasticPointDetailUserEvent
    data object DestinationIconClicked : JasticPointDetailUserEvent
    data class DestinationSavingOptionsChanged(val state: DestinationSavingOptions) :
        JasticPointDetailUserEvent
}

sealed interface JasticPointDetailNavState {
    data object Idle : JasticPointDetailNavState
    data object ToMyJasticList : JasticPointDetailNavState
    data class ToDestinationSelectionMap(
        val latitude: Double, val longitude: Double, val radiusInMeters: Float
    ) : JasticPointDetailNavState
}

enum class DestinationSavingOptions { Idle, Save, Update }