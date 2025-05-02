package com.example.visionscan.ui.oo.navigate


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.visionscan.ui.oo.screen.ImagePicker.ImagePickerScreen
import com.example.visionscan.ui.oo.screen.Main.MainScreen
import com.example.visionscan.ui.oo.screen.Main.MainViewModel
import com.example.visionscan.ui.oo.screen.ScanHistory.ScanHistoryScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { MainScreen(navController) }
        composable("image_picker") { ImagePickerScreen(navController) }
        composable("scan_history") { ScanHistoryScreen(navController) }
    }
}