package com.rafaelduransaez.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jastic_destinations")
data class JasticDestinationEntity(
    @PrimaryKey
    val id: Int,
    val alias: String,
    val contactName: String,
    val contactPhone: String
)