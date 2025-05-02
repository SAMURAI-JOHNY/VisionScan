package com.example.visionscan.ui.oo.screen.ScanHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visionscan.ui.oo.screen.ScanHistory.DetectedObject
import com.example.visionscan.ui.oo.screen.ScanHistory.RecognitionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val _history = MutableStateFlow<List<RecognitionItem>>(emptyList())
    val history: StateFlow<List<RecognitionItem>> = _history.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            // Здесь должна быть реальная загрузка
            _history.value = dummyHistoryData()
        }
    }

    private fun dummyHistoryData(): List<RecognitionItem> {
        return listOf(
            RecognitionItem(
                id = "1",
                imageUri = "",
                timestamp = System.currentTimeMillis() - 100000,
                detectedObjects = listOf(
                    DetectedObject("Кошка", 0.92f),
                    DetectedObject("Диван", 0.87f)
                )
            ),
            RecognitionItem(
                id = "2",
                imageUri = "",
                timestamp = System.currentTimeMillis() - 500000,
                detectedObjects = listOf(
                    DetectedObject("Собака", 0.95f),
                    DetectedObject("Мяч", 0.78f)
                )
            )
        )
    }
}