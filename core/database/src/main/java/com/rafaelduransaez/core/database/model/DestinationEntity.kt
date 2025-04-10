package com.rafaelduransaez.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rafaelduransaez.core.domain.extensions.zero

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = Long.zero,
    val alias: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val radiusInMeters: Float
)