package com.rafaelduransaez.core.database.model

import androidx.room.Embedded
import androidx.room.Relation

class JasticDestinationPoint(
    @Embedded val point: JasticPointEntity,
    @Relation(
        parentColumn = "destinationId",
        entityColumn = "id"
    )
    val destination: DestinationEntity
)


