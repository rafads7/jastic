package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.usecase.JasticFlowUseCase
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJasticPointsListUseCase @Inject constructor(
    private val repository: MyJasticRepository
) : JasticFlowUseCase<Unit, List<JasticPointListItemUI>, DatabaseError>() {

    override suspend fun execute(params: Unit): Flow<JasticResult<List<JasticPointListItemUI>, DatabaseError>> {
        return repository.getAll()
    }
}