package com.rafaelduransaez.feature.myjastic.data.repository

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.util.safeDbCall
import com.rafaelduransaez.core.database.util.safeDbFlowCall
import com.rafaelduransaez.feature.myjastic.data.source.MyJasticLocalDataSource
import com.rafaelduransaez.feature.myjastic.data.toDestinationEntity
import com.rafaelduransaez.feature.myjastic.data.toJasticListItemUI
import com.rafaelduransaez.feature.myjastic.data.toJasticPointEntity
import com.rafaelduransaez.feature.myjastic.data.toJasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.DestinationUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointUI
import com.rafaelduransaez.feature.myjastic.domain.model.JasticPointListItemUI
import com.rafaelduransaez.feature.myjastic.domain.repository.MyJasticRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyJasticRepositoryImpl(
    private val myJasticDataSource: MyJasticLocalDataSource
) : MyJasticRepository {

    override fun getJasticPoint(jasticPointId: Long): Flow<JasticResult<JasticPointUI, DatabaseError>> =
        safeDbFlowCall {
            myJasticDataSource.getJasticPointDetail(jasticPointId).map { it.toJasticPointUI() }
        }

    override fun getAll(): Flow<JasticResult<List<JasticPointListItemUI>, DatabaseError>> =
        safeDbFlowCall {
            myJasticDataSource.getJasticPoints().map {
                it.map(JasticDestinationPoint::toJasticListItemUI)
            }
        }

    override suspend fun saveJasticPoint(jasticPoint: JasticPointUI): JasticResult<Long, DatabaseError> =
        safeDbCall {
            myJasticDataSource.saveJasticPointAndDestination(
                jasticPoint.toJasticPointEntity(),
                jasticPoint.destinationUI.toDestinationEntity()
            )
        }
}