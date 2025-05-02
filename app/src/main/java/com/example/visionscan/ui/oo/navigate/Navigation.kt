package com.example.visionscan.ui.oo.navigate

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.visionscan.ui.oo.screen.ImagePicker.ImagePickerScreen
import com.example.visionscan.ui.oo.screen.ImageViewer.ImageViewerScreen
import com.example.visionscan.ui.oo.screen.Main.MainScreen
import com.example.visionscan.ui.oo.screen.ScanHistory.ScanHistoryScreen
import androidx.navigation.NavType

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "image_picker"
    ) {
        composable("home") { MainScreen(navController) }
        composable("image_picker") { ImagePickerScreen(navController) }
        composable("scan_history") { ScanHistoryScreen(navController) }
        composable(
            "image_viewer/{imageUri}",
            arguments = listOf(navArgument("imageUri") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val encodedUri = backStackEntry.arguments?.getString("imageUri")
            val decodedUri = encodedUri?.let { Uri.parse(it) }

            if (decodedUri != null) {
                ImageViewerScreen(
                    navController = navController,
                    imageUri = decodedUri
                )
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка загрузки изображения")
                }
            }
        }
    }
}
