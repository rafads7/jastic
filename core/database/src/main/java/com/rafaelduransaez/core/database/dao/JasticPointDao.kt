package com.rafaelduransaez.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticDestinationPoint
import com.rafaelduransaez.core.database.model.JasticPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JasticPointDao {

    @Query("SELECT * FROM jastic_points WHERE id = :id LIMIT 1")
    fun get(id: Long): Flow<JasticDestinationPoint>

    @Query("SELECT * FROM jastic_points")
    fun getAll(): Flow<List<JasticDestinationPoint>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(point: JasticPointEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(point: DestinationEntity): Long

    @Transaction
    suspend fun upsert(point: JasticPointEntity, destination: DestinationEntity): Long {
        val destinationId = upsert(destination)
        val jasticPointWithId = point.copy(destinationId = destinationId)
        return upsert(jasticPointWithId)
    }

    @Query("DELETE FROM jastic_points")
    suspend fun clearAll(): Int
}