package com.rafaelduransaez.feature.saved_destinations.domain.usecases

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.usecase.JasticFlowUseCase
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI
import com.rafaelduransaez.feature.saved_destinations.domain.repository.SavedDestinationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class GetSavedDestinationsUseCase: JasticFlowUseCase<Unit, List<DestinationsListItemUI>, DatabaseError>()

class GetSavedDestinationsUseCaseImpl @Inject constructor(
    private val repository: SavedDestinationsRepository
) : GetSavedDestinationsUseCase() {

    override suspend fun execute(params: Unit): Flow<JasticResult<List<DestinationsListItemUI>, DatabaseError>> {
        return repository.getAll()
    }
}