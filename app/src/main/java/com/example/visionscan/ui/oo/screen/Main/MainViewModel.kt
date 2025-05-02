// MainViewModel.kt
package com.example.visionscan.ui.oo.screen.Main

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun navigateToImagePicker(navController: NavController) {
        // Здесь можно добавить логику перед навигацией
        // Например, проверку разрешений камеры
        navController.navigate("image_picker")
    }
}