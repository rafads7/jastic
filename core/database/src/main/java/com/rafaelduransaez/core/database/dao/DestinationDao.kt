package com.rafaelduransaez.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelduransaez.core.database.model.DestinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {

    @Query("SELECT * FROM destinations WHERE id = :id")
    fun get(id: Long): Flow<DestinationEntity>

    @Query("SELECT * FROM destinations")
    fun getAll(): Flow<List<DestinationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(destinations: List<DestinationEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(destination: DestinationEntity): Long

    @Query("DELETE FROM destinations")
    fun clearAll(): Int
}
