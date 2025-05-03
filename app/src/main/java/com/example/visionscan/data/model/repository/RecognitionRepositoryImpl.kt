package com.example.visionscan.data.repository

import android.content.Context
import com.example.visionscan.data.database.RecognitionDatabase
import com.example.visionscan.data.model.database.RecognitionDao
import com.example.visionscan.data.model.database.toEntity
import com.example.visionscan.data.model.database.toDomain
import com.example.visionscan.data.model.model.RecognitionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecognitionRepositoryImpl private constructor(
    private val dao: RecognitionDao
) : RecognitionRepository {

    companion object {
        @Volatile
        private var instance: RecognitionRepositoryImpl? = null

        fun getInstance(context: Context): RecognitionRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: RecognitionRepositoryImpl(
                    RecognitionDatabase.getInstance(context).recognitionDao()
                ).also { instance = it }
            }
        }
    }

    override suspend fun saveRecognition(result: RecognitionResult) {
        dao.insert(result.toEntity())
    }

    override fun getAllRecognitions(): Flow<List<RecognitionResult>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() } // Убедитесь, что toDomain() возвращает RecognitionResult
        }
    }

    override suspend fun deleteRecognition(id: String) {
        dao.deleteById(id)
    }
}