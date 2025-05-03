package com.example.visionscan.ui.oo.screen.ImageViewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visionscan.data.model.model.RecognitionResult

import com.example.visionscan.data.repository.RecognitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(
    private val repository: RecognitionRepository
) : ViewModel() {

    fun saveRecognition(result: RecognitionResult) {
        viewModelScope.launch {
            repository.saveRecognition(result)
        }
    }
}