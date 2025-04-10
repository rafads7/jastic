package com.rafaelduransaez.feature.saved_destinations.domain.usecases

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.usecase.JasticFlowUseCase
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.domain.repository.SavedDestinationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class GetSavedDestinationUseCase: JasticFlowUseCase<Long, DestinationUI, DatabaseError>()

class GetSavedDestinationUseCaseImpl @Inject constructor(
    private val repository: SavedDestinationsRepository
) : GetSavedDestinationUseCase() {

    override suspend fun execute(params: Long): Flow<JasticResult<DestinationUI, DatabaseError>> {
        return repository.getById(params)
    }

}