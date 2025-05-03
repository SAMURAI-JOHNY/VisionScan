package com.example.visionscan.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.visionscan.data.model.database.RecognitionDao
import com.example.visionscan.data.model.database.RecognitionEntity

@Database(entities = [RecognitionEntity::class], version = 1, exportSchema = false)
abstract class RecognitionDatabase : RoomDatabase() {
    abstract fun recognitionDao(): RecognitionDao

    companion object {
        @Volatile private var instance: RecognitionDatabase? = null

        fun getInstance(context: Context): RecognitionDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecognitionDatabase::class.java,
                    "visionscan.db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}