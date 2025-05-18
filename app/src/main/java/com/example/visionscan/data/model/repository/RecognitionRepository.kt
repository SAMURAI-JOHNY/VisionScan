package com.example.visionscan.data.repository

import android.net.Uri
import com.example.visionscan.data.model.database.LabelResult
import com.example.visionscan.data.model.database.ObjectResult
import com.example.visionscan.data.model.database.RecognitionDao
import com.example.visionscan.data.model.database.RecognitionEntity
import com.example.visionscan.data.model.database.RecognitionWithDetails
import kotlinx.coroutines.flow.Flow

// RecognitionRepository.kt
class RecognitionRepository(private val dao: RecognitionDao) {
    val allRecognitions: Flow<List<RecognitionWithDetails>> = dao.getAllWithDetails()

    suspend fun saveRecognitionWithDetails(
        imageUri: Uri,
        labels: List<LabelResult>,
        objects: List<ObjectResult>
    ) {
        dao.insertFullRecognition(
            recognition = RecognitionEntity(imageUri = imageUri),
            labels = labels,
            objects = objects
        )
    }

    suspend fun deleteRecognition(id: Long) {
        dao.deleteById(id)
    }

    suspend fun insertRecognition(recognition: RecognitionEntity): Long {
        return dao.insertRecognition(recognition)
    }

    suspend fun insertLabel(label: LabelResult) {
        dao.insertLabel(label)
    }

    suspend fun insertObject(obj: ObjectResult) {
        dao.insertObject(obj)
    }

    fun getAllRecognitionsWithDetails(): Flow<List<RecognitionWithDetails>> {
        return dao.getAllWithDetails()
    }


}