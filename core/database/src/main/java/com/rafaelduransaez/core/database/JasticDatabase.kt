package com.rafaelduransaez.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelduransaez.core.database.dao.JasticDestinationDao
import com.rafaelduransaez.core.database.model.JasticDestinationEntity

@Database(entities = [JasticDestinationEntity::class], version = 1)
abstract class JasticDatabase: RoomDatabase() {
    abstract fun jasticDestinationDao(): JasticDestinationDao
}