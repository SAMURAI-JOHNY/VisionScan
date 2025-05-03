package com.example.visionscan.data.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecognitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recognition: RecognitionEntity)

    @Query("SELECT * FROM recognitions ORDER BY timestamp DESC")
    fun getAll(): Flow<List<RecognitionEntity>>

    @Query("DELETE FROM recognitions WHERE id = :id")
    suspend fun deleteById(id: String)
}