package com.rafaelduransaez.feature.myjastic.data.source

import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import kotlinx.coroutines.flow.Flow

interface MyJasticLocalDataSource {

    fun getJasticPointDetail(jasticPointId: Long): Flow<JasticDestinationPoint>

    fun getJasticPoints(): Flow<List<JasticDestinationPoint>>

    suspend fun saveJasticPoint(jasticPoint: JasticPointEntity): Long

    suspend fun saveJasticPointAndDestination(jasticPoint: JasticPointEntity, destinationEntity: DestinationEntity): Long
}
