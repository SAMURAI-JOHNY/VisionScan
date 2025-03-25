package com.example.visionscan.ui.oo.screen.ImagePicker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.visionscan.ui.oo.util.showToast // Правильный импорт

@Composable
fun ImagePickerScreen(
    navController: NavController,
    viewModel: ImagePickerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            navController.navigate("image_preview/${it.toString()}")
        } ?: run {
            context.showToast("Изображение не выбрано") // Используем extension
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Выбрать из галереи")
        }

        Button(
            onClick = { /* Логика камеры будет здесь */ },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Сделать фото")
        }
    }
}