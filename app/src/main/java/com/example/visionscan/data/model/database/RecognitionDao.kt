package com.example.visionscan.data.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecognitionDao {
    @Insert
    suspend fun insertRecognition(recognition: RecognitionEntity): Long

    @Insert
    suspend fun insertLabel(label: LabelResult)

    @Insert
    suspend fun insertObject(obj: ObjectResult)

    @Transaction
    @Query("SELECT * FROM recognitions ORDER BY timestamp DESC")
    fun getAllWithDetails(): Flow<List<RecognitionWithDetails>>

    @Query("DELETE FROM recognitions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Transaction
    suspend fun insertFullRecognition(
        recognition: RecognitionEntity,
        labels: List<LabelResult>,
        objects: List<ObjectResult>
    ) {
        val recognitionId = insertRecognition(recognition)

        labels.forEach { label ->
            insertLabel(label.copy(recognitionId = recognitionId))
        }

        objects.forEach { obj ->
            insertObject(obj.copy(recognitionId = recognitionId))
        }
    }
}