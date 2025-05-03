package com.example.visionscan.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.visionscan.data.repository.RecognitionRepository
import com.example.visionscan.data.repository.RecognitionRepositoryImpl
import com.example.visionscan.ui.oo.screen.ImagePicker.ImagePickerScreen
import com.example.visionscan.ui.oo.screen.ImageViewer.ImageViewerScreen
import com.example.visionscan.ui.oo.screen.Main.MainScreen
import com.example.visionscan.ui.oo.screen.ScanHistory.ScanHistoryScreen
import com.example.visionscan.ui.oo.screen.ScanHistory.ScanHistoryViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // 1. Получаем экземпляр репозитория
    val repository = remember {
        RecognitionRepositoryImpl.getInstance(context)
    }

    // 2. Настраиваем навигацию
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MainScreen(
                navController = navController
            )
        }

        // 3. Экран истории сканирований
        composable("scan_history") {
            val viewModel = viewModel<ScanHistoryViewModel>(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        // 4. Передаем репозиторий в ViewModel
                        return ScanHistoryViewModel(repository) as T
                    }
                }
            )

            ScanHistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable("image_picker") {
            ImagePickerScreen(
                navController = navController
            )
        }
    }
}