package com.example.visionscan.data.model.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recognitions")
data class RecognitionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val imageUri: Uri,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "labels",
    foreignKeys = [ForeignKey(
        entity = RecognitionEntity::class,
        parentColumns = ["id"],
        childColumns = ["recognitionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LabelResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recognitionId: Long,
    val text: String,
    val confidence: Float
)

@Entity(
    tableName = "objects",
    foreignKeys = [ForeignKey(
        entity = RecognitionEntity::class,
        parentColumns = ["id"],
        childColumns = ["recognitionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ObjectResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recognitionId: Long,
    val text: String,
    val confidence: Float,
    val boundingBox: String? = null
)