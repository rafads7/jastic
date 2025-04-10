package com.rafaelduransaez.feature.myjastic.data.source

import com.rafaelduransaez.core.database.dao.JasticPointDao
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import kotlinx.coroutines.flow.Flow

class RoomDataSource(
    private val jasticDao: JasticPointDao
) : MyJasticLocalDataSource {

    override fun getJasticPointDetail(jasticPointId: Long): Flow<JasticDestinationPoint> =
        jasticDao.get(jasticPointId)

    override fun getJasticPoints(): Flow<List<JasticDestinationPoint>> =
        jasticDao.getAll()

    override suspend fun saveJasticPoint(jasticPoint: JasticPointEntity): Long =
        jasticDao.upsert(jasticPoint)

    override suspend fun saveJasticPointAndDestination(jasticPoint: JasticPointEntity, destinationEntity: DestinationEntity): Long =
        jasticDao.upsert(jasticPoint, destinationEntity)

}