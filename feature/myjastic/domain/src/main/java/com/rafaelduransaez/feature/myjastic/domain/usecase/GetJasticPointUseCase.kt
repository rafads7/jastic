package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.usecase.JasticFlowUseCase
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJasticPointUseCase @Inject constructor(
    private val repository: MyJasticRepository
): JasticFlowUseCase<Long, JasticPointUI, DatabaseError>() {

    override suspend fun execute(params: Long): Flow<JasticResult<JasticPointUI, DatabaseError>> {
        return repository.getJasticPoint(params)
    }
}
