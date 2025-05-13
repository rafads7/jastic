package com.rafaelduransaez.feature.myjastic.data.source

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import kotlinx.coroutines.flow.Flow

interface MyJasticLocalDataSource {

    fun getJasticPointDetail(jasticPointId: Long): Flow<JasticResult<JasticPointUI, DatabaseError>>

    fun getJasticPoints(): Flow<JasticResult<List<JasticPointListItemUI>, DatabaseError>>

    suspend fun saveJasticPointAndDestination(
        jasticPoint: JasticPointEntity,
        destinationEntity: DestinationEntity
    ): JasticResult<Long, DatabaseError>
}
