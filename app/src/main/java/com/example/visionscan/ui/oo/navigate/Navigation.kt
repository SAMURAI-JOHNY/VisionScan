package com.example.visionscan.ui.oo.navigate


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.visionscan.ui.oo.screen.MainScreen
import com.example.visionscan.ui.oo.screen.ImagePicker.ImagePickerScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "image_picker"
    ) {
        composable("home") { MainScreen(navController) }
        composable("image_picker") { ImagePickerScreen(navController) }
    }
}