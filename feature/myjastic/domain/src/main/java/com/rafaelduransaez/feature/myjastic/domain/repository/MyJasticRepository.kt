package com.rafaelduransaez.feature.myjastic.domain.repository

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import kotlinx.coroutines.flow.Flow

interface MyJasticRepository {
    fun getAll(): Flow<JasticResult<List<JasticPointListItemUI>, DatabaseError>>
    fun getJasticPoint(jasticPointId: Long): Flow<JasticResult<JasticPointUI, DatabaseError>>
    suspend fun saveJasticPoint(jasticPoint: JasticPointUI): JasticResult<Long, DatabaseError>
}