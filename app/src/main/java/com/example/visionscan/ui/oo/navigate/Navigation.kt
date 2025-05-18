package com.example.visionscan.ui.navigation

import android.content.Context
import android.net.Uri
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
import com.example.visionscan.data.model.database.RecognitionWithDetails
import com.example.visionscan.data.repository.RecognitionRepository
import com.example.visionscan.ui.oo.screen.ImagePicker.ImagePickerScreen
import com.example.visionscan.ui.oo.screen.ImageViewer.ImageViewerScreen
import com.example.visionscan.ui.oo.screen.Main.MainScreen
import com.example.visionscan.ui.oo.screen.ScanHistoryScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MainScreen(
                navController = navController
            )
        }

        composable("scan_history") {

            ScanHistoryScreen(
                navController = navController,
            )
        }

        composable("image_picker") {
            ImagePickerScreen(
                navController = navController
            )
        }

        composable(
            "image_viewer/{encodedUri}",
            arguments = listOf(navArgument("encodedUri") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUri = backStackEntry.arguments?.getString("encodedUri") ?: ""
            val imageUri = Uri.parse(Uri.decode(encodedUri))
            ImageViewerScreen(
                navController = navController,
                imageUri = imageUri
            )
        }
    }
}