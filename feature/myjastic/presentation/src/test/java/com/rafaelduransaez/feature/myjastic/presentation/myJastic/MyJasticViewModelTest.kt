package com.rafaelduransaez.feature.myjastic.presentation.myJastic

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult.Failure
import com.rafaelduransaez.core.base.models.JasticResult.Success
import com.rafaelduransaez.core.base.test_utils.JasticTest
import com.rafaelduransaez.feature.myjastic.domain.usecase.GetJasticPointsListUseCase
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticUiState.Error
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticUiState.Loading
import com.rafaelduransaez.feature.myjastic.presentation.myJastic.MyJasticUiState.ShowMyJasticPoints
import com.rafaelduransaez.feature.myjastic.presentation.utils.mockJasticPointsList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class MyJasticViewModelTest: JasticTest<MyJasticViewModel, MyJasticUiState, MyJasticNavState>() {

    private val getJasticPointsListUseCase = mock<GetJasticPointsListUseCase>()

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = MyJasticViewModel(getJasticPointsListUseCase)
    }

    @Test
    fun `Initialization should first update with Loading state`() = runTest {
        whenever(getJasticPointsListUseCase()).doReturn(flowOf(Success(mockJasticPointsList)))

        collectViewModelState(viewModel.uiState)

        val resultState = emittedStates.first()

        assertEquals(Loading, resultState)
    }

    @Test
    fun `uiState emits Loading then ShowMyJasticPoints on success load`() = runTest {
        whenever(getJasticPointsListUseCase()).doReturn(flowOf(Success(mockJasticPointsList)))

        collectViewModelState(viewModel.uiState)
        val expectedResult = listOf(Loading, ShowMyJasticPoints(mockJasticPointsList))

        assertEquals(expectedResult, emittedStates)
    }

    @Test
    fun `uiState emits Loading then Error on failed load`() = runTest {
        whenever(getJasticPointsListUseCase()).doReturn(flowOf(Failure(DatabaseError.UNKNOWN)))

        collectViewModelState(viewModel.uiState)
        val expectedResult = listOf(Loading, Error)

        assertEquals(expectedResult, emittedStates)
    }

}

