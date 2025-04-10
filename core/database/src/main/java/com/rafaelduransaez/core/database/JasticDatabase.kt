package com.rafaelduransaez.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelduransaez.core.database.dao.DestinationDao
import com.rafaelduransaez.core.database.dao.JasticPointDao
import com.rafaelduransaez.core.database.model.DestinationEntity
import com.rafaelduransaez.core.database.model.JasticPointEntity

@Database(entities = [JasticPointEntity::class, DestinationEntity::class], version = 1)
abstract class JasticDatabase : RoomDatabase() {
    abstract fun jasticPointDao(): JasticPointDao
    abstract fun destinationDao(): DestinationDao
}