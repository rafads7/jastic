package com.rafaelduransaez.feature.saved_destinations.data.repository

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.util.safeDbFlowCall
import com.rafaelduransaez.feature.saved_destinations.data.source.SavedDestinationsLocalDataSource
import com.rafaelduransaez.feature.saved_destinations.data.toDestinationUI
import com.rafaelduransaez.feature.saved_destinations.data.toDestinationsListItemUI
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI
import com.rafaelduransaez.feature.saved_destinations.domain.repository.SavedDestinationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SavedDestinationsRepositoryImpl(
    private val localDataSource: SavedDestinationsLocalDataSource
) : SavedDestinationsRepository {

    override fun getAll(): Flow<JasticResult<List<DestinationsListItemUI>, DatabaseError>> {
        return safeDbFlowCall {
            localDataSource.getAll().map { it.map(DestinationEntity::toDestinationsListItemUI) }
        }
    }

    override fun getById(id: Long): Flow<JasticResult<DestinationUI, DatabaseError>> {
        return safeDbFlowCall {
            localDataSource.getById(id).map { it.toDestinationUI() }
        }
    }
}