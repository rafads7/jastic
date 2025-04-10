package com.rafaelduransaez.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rafaelduransaez.core.domain.extensions.zero

@Entity(
    tableName = "jastic_points",
    foreignKeys = [
        ForeignKey(
            entity = DestinationEntity::class,
            parentColumns = ["id"],
            childColumns = ["destinationId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("destinationId")]
)
data class JasticPointEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = Long.zero,
    val alias: String,
    val contactPhone: String,
    val contactName: String,
    val message: String,
    val destinationId: Long
)