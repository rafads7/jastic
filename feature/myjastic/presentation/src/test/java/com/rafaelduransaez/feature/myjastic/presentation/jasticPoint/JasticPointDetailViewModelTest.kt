package com.rafaelduransaez.feature.myjastic.presentation.jasticPoint

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.models.JasticResult.Success
import com.rafaelduransaez.core.base.test_utils.JasticTest
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetContactInfoUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetJasticPointUseCase
import com.rafaelduransaez.feature.myjastic.domain.usecase.SaveJasticPointUseCase
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.DestinationSavingOptions.Idle
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailNavState.ToDestinationSelectionMap
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailNavState.ToMyJasticList
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.AliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationAliasUpdate
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationIconClicked
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.DestinationSavingOptionsChanged
import com.rafaelduransaez.feature.myjastic.presentation.jasticPoint.JasticPointDetailUserEvent.MessageUpdate
import com.rafaelduransaez.feature.myjastic.presentation.utils.mockContact
import com.rafaelduransaez.feature.myjastic.presentation.utils.mockGeofenceLocation
import com.rafaelduransaez.feature.myjastic.presentation.utils.mockJasticPointUI1
import com.rafaelduransaez.feature.myjastic.presentation.utils.toStateWithJasticPointDetailData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class JasticPointDetailViewModelTest :
    JasticTest<JasticPointDetailViewModel, JasticPointDetailUiState, JasticPointDetailNavState>() {

    private val getContactInfoUseCase: GetContactInfoUseCase = mock()
    private val getJasticPointUseCase: GetJasticPointUseCase = mock()
    private val saveJasticPointUseCase: SaveJasticPointUseCase = mock()

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = JasticPointDetailViewModel(
            jasticPointId = mockJasticPointUI1.id,
            getContactInfoUseCase = getContactInfoUseCase,
            getJasticPointUseCase = getJasticPointUseCase,
            saveJasticPointUseCase = saveJasticPointUseCase
        )
    }

    @Test
    fun `Initialization should first update with Loading state`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        val emittedStates = viewModel.uiState.value
        val expectedState = JasticPointDetailUiState(isLoading = true)

        assertEquals(expectedState.isLoading, emittedStates.isLoading)
    }

    @Test
    fun `on downloading JasticPoint detail success loads all its data`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        val successState = emittedStates.last()
        val expectedState = mockJasticPointUI1.toStateWithJasticPointDetailData()

        with(successState) {
            assertEquals(expectedState.isLoading, isLoading)
            assertEquals(expectedState.jasticPointId, jasticPointId)
            assertEquals(expectedState.jasticPointAlias, jasticPointAlias)
            assertEquals(expectedState.contactAlias, contactAlias)
            assertEquals(expectedState.contactPhoneNumber, contactPhoneNumber)
            assertEquals(expectedState.destinationId, destinationId)
            assertEquals(expectedState.destinationAlias, destinationAlias)
            assertEquals(expectedState.geofenceLocation.latitude, geofenceLocation.latitude, 0.0)
            assertEquals(expectedState.geofenceLocation.longitude, geofenceLocation.longitude, 0.0)
            assertEquals(expectedState.geofenceLocation.address, geofenceLocation.address)
            assertEquals(
                expectedState.geofenceLocation.radiusInMeters,
                geofenceLocation.radiusInMeters
            )
        }
    }

    @Test
    fun `on downloading JasticPoint detail shows error when failing`() = runTest {
        whenever(getJasticPointUseCase(anyLong()))
            .doReturn(flowOf(JasticResult.Failure(DatabaseError.UNKNOWN)))

        collectViewModelState(viewModel.uiState)

        val successState = emittedStates.last()
        val expectedState = JasticPointDetailUiState(error = true)

        with(successState) {
            assertEquals(expectedState.isLoading, isLoading)
            assertEquals(expectedState.error, error)
        }
    }

    @Test
    fun `on getting contact successfully saves its name and phone number`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))
        whenever(getContactInfoUseCase(anyString())).doReturn(Success(mockContact))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(JasticPointDetailUserEvent.ContactSelected(mockContact.id))

        val successState = emittedStates.last()

        with(successState) {
            assertEquals(mockContact.name, contactAlias)
            assertEquals(mockContact.phoneNumber, contactPhoneNumber)
        }
    }

    @Test
    fun `on selecting destination successfully saves the geolocation data`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(JasticPointDetailUserEvent.DestinationSelected(mockGeofenceLocation))

        val successState = emittedStates.last()

        with(successState) {
            assertEquals(mockGeofenceLocation.address, geofenceLocation.address)
            assertEquals(mockGeofenceLocation.longitude, geofenceLocation.longitude, 0.0)
            assertEquals(mockGeofenceLocation.latitude, geofenceLocation.latitude, 0.0)
            assertEquals(mockGeofenceLocation.radiusInMeters, geofenceLocation.radiusInMeters)
        }
    }

    @Test
    fun `on saving JasticPoint detail successfully navigates to MyJastic list`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))
        whenever(saveJasticPointUseCase(any())).doReturn(Success(mockJasticPointUI1.id))

        collectViewModelState(viewModel.uiState)
        collectNavState(viewModel)

        viewModel.onUiEvent(JasticPointDetailUserEvent.Save)

        assertEquals(ToMyJasticList, navStates.last())
    }

    @Test
    fun `on saving JasticPoint detail fails removes loading`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))
        whenever(saveJasticPointUseCase(any())).doReturn(Success(mockJasticPointUI1.id))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(JasticPointDetailUserEvent.Save)

        assertEquals(false, emittedStates.last().isLoading)
    }


    @Test
    fun `on updating message updates it within the state`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(MessageUpdate(mockJasticPointUI1.message))

        assertEquals(mockJasticPointUI1.message, emittedStates.last().jasticPointMessage)
    }

    @Test
    fun `on updating destination alias updates it within the state`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(DestinationAliasUpdate(mockJasticPointUI1.destinationUI.alias))

        assertEquals(
            mockJasticPointUI1.destinationUI.alias,
            emittedStates.last().destinationAlias
        )
    }

    @Test
    fun `on updating alias updates it within the state`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(AliasUpdate(mockJasticPointUI1.alias))

        assertEquals(mockJasticPointUI1.alias, emittedStates.last().jasticPointAlias)
    }

    @Test
    fun `on updating destination saving options updates it within the state`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)

        viewModel.onUiEvent(DestinationSavingOptionsChanged(Idle))

        assertEquals(Idle, emittedStates.last().destinationSavingOptions)
    }

    @Test
    fun `on clicking destination icon navigates to map destination selection`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)
        collectNavState(viewModel)

        viewModel.onUiEvent(DestinationIconClicked)

        with(mockJasticPointUI1.destinationUI) {
            assertEquals(
                ToDestinationSelectionMap(latitude, longitude, radiusInMeters),
                navStates.last()
            )
        }
    }

    @Test
    fun `on back navigates to my jastic list`() = runTest {
        whenever(getJasticPointUseCase(anyLong())).doReturn(flowOf(Success(mockJasticPointUI1)))

        collectViewModelState(viewModel.uiState)
        collectNavState(viewModel)

        viewModel.onUiEvent(JasticPointDetailUserEvent.Back)

        assertEquals(ToMyJasticList, navStates.last())
    }
}