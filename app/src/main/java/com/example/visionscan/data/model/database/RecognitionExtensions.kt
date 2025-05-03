package com.example.visionscan.data.model.database

import com.example.visionscan.data.model.model.RecognitionResult


fun RecognitionEntity.toDomain() = RecognitionResult(
    id, imageUri, recognitionText, timestamp, additionalData
)

fun RecognitionResult.toEntity() = RecognitionEntity(
    id, imageUri, recognitionText, timestamp, additionalData
)