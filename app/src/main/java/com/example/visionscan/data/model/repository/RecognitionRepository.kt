package com.example.visionscan.data.repository

import com.example.visionscan.data.model.model.RecognitionResult
import kotlinx.coroutines.flow.Flow


interface RecognitionRepository {
    suspend fun saveRecognition(result: RecognitionResult)
    fun getAllRecognitions(): Flow<List<RecognitionResult>>
    suspend fun deleteRecognition(id: String)
}