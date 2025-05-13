package com.rafaelduransaez.feature.myjastic.data.source

import com.rafaelduransaez.core.database.dao.JasticPointDao
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import com.rafaelduransaez.core.database.util.safeDbCall
import com.rafaelduransaez.core.database.util.safeDbFlowCall
import com.rafaelduransaez.feature.myjastic.data.toJasticListItemUI
import com.rafaelduransaez.feature.myjastic.data.toJasticPointUI
import kotlinx.coroutines.flow.map

class RoomDataSource(
    private val jasticDao: JasticPointDao
) : MyJasticLocalDataSource {

    override fun getJasticPointDetail(jasticPointId: Long) = safeDbFlowCall {
        jasticDao.get(jasticPointId).map { it.toJasticPointUI() }
    }

    override fun getJasticPoints() = safeDbFlowCall {
        jasticDao.getAll().map { it.map(JasticDestinationPoint::toJasticListItemUI) }
    }

    override suspend fun saveJasticPointAndDestination(
        jasticPoint: JasticPointEntity,
        destinationEntity: DestinationEntity
    ) = safeDbCall { jasticDao.upsert(jasticPoint, destinationEntity) }

}