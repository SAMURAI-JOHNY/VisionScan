package com.example.visionscan.data.model.database

import androidx.room.Embedded
import androidx.room.Relation


data class RecognitionWithDetails(
    @Embedded val recognition: RecognitionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recognitionId"
    )
    val labels: List<LabelResult>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recognitionId",
    )
    val objects: List<ObjectResult>
)