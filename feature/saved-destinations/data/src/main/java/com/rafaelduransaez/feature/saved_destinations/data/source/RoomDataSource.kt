package com.rafaelduransaez.feature.saved_destinations.data.source

import com.rafaelduransaez.core.database.dao.DestinationDao
import com.rafaelduransaez.core.database.model.DestinationEntity
import kotlinx.coroutines.flow.Flow

class RoomDataSource(
    private val destinationsDao: DestinationDao
): SavedDestinationsLocalDataSource {

    override fun getAll(): Flow<List<DestinationEntity>> {
        return destinationsDao.getAll()
    }

    override fun getById(id: Long): Flow<DestinationEntity> {
        return destinationsDao.get(id)
    }

}