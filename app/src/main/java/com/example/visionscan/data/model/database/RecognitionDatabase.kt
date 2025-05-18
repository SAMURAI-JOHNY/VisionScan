package com.example.visionscan.data.database

import android.content.Context
import android.net.Uri
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.visionscan.data.model.database.LabelResult
import com.example.visionscan.data.model.database.ObjectResult
import com.example.visionscan.data.model.database.RecognitionDao
import com.example.visionscan.data.model.database.RecognitionEntity

// AppDatabase.kt
@Database(
    entities = [RecognitionEntity::class, LabelResult::class, ObjectResult::class],
    version = 2
)
@TypeConverters(UriConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recognitionDao(): RecognitionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "visionscan_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}