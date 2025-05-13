package com.rafaelduransaez.feature.myjastic.data.repository

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.feature.myjastic.data.source.MyJasticLocalDataSource
import com.rafaelduransaez.feature.myjastic.data.toDestinationEntity
import com.rafaelduransaez.feature.myjastic.data.toJasticPointEntity
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import kotlinx.coroutines.flow.Flow

class MyJasticRepositoryImpl(
    private val myJasticDataSource: MyJasticLocalDataSource
) : MyJasticRepository {

    override fun getJasticPoint(jasticPointId: Long) =
        myJasticDataSource.getJasticPointDetail(jasticPointId)

    override fun getAll(): Flow<JasticResult<List<JasticPointListItemUI>, DatabaseError>> =
        myJasticDataSource.getJasticPoints()

    override suspend fun saveJasticPoint(jasticPoint: JasticPointUI): JasticResult<Long, DatabaseError> =
        myJasticDataSource.saveJasticPointAndDestination(
            jasticPoint.toJasticPointEntity(),
            jasticPoint.destinationUI.toDestinationEntity()
        )
}