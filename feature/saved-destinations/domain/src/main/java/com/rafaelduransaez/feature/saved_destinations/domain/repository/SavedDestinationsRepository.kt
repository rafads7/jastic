package com.rafaelduransaez.feature.saved_destinations.domain.repository

import com.rafaelduransaez.core.base.models.DatabaseError
import com.rafaelduransaez.core.base.models.JasticResult
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationUI
import com.rafaelduransaez.feature.saved_destinations.domain.model.DestinationsListItemUI
import kotlinx.coroutines.flow.Flow

interface SavedDestinationsRepository {
    fun getAll(): Flow<JasticResult<List<DestinationsListItemUI>, DatabaseError>>
    fun getById(id: Long): Flow<JasticResult<DestinationUI, DatabaseError>>
}