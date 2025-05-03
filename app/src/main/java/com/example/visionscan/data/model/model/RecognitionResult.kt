package com.example.visionscan.data.model.model

import java.util.UUID


data class RecognitionResult(
    val id: String = UUID.randomUUID().toString(),
    val imageUri: String, // URI изображения
    val recognitionText: String, // Распознанный текст
    val timestamp: Long = System.currentTimeMillis(), // Время распознавания
    val additionalData: Map<String, String> = emptyMap() // Дополнительные данные
)