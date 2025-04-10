package com.rafaelduransaez.feature.saved_destinations.data.source

import com.rafaelduransaez.core.database.model.DestinationEntity
import kotlinx.coroutines.flow.Flow

interface SavedDestinationsLocalDataSource {
    fun getAll(): Flow<List<DestinationEntity>>
    fun getById(id: Long): Flow<DestinationEntity>
}
