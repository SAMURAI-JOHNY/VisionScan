package com.example.visionscan.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.visionscan.data.model.database.converters.MapConverter
import java.util.*

@Entity(tableName = "recognitions")
@TypeConverters(MapConverter::class)
data class RecognitionEntity (
    @PrimaryKey val id: String,
    val imageUri: String,
    val recognitionText: String,
    val timestamp: Long,
    val additionalData: Map<String, String>
)