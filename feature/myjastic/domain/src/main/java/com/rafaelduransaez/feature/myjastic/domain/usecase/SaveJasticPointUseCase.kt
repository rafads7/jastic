package com.rafaelduransaez.feature.myjastic.domain.usecase

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.base.usecase.JasticUseCase
import com.rafaelduransaez.feature.myjastic.domain.model.DestinationUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import javax.inject.Inject

class SaveJasticPointUseCase @Inject constructor(
        private val repository: MyJasticRepository
    ): JasticUseCase<JasticPointUI, Long, DatabaseError>() {

        override suspend fun execute(params: JasticPointUI): JasticResult<Long, DatabaseError> {
            return repository.saveJasticPoint(params)
        }
    }