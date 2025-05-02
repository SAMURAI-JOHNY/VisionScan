package com.example.visionscan.ui.oo.screen.ScanHistory

import java.text.SimpleDateFormat
import java.util.*

data class RecognitionItem(
    val id: String,
    val imageUri: String,
    val timestamp: Long,
    val detectedObjects: List<DetectedObject>
)

data class DetectedObject(
    val name: String,
    val confidence: Float
) {
    fun confidencePercent() = (confidence * 100).toInt()
}


fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}