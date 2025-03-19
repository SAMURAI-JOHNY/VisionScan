package com.example.visionscan.ui.oo.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.visionscan.ui.oo.screen.ImageScreen
import com.example.visionscan.ui.oo.screen.MainScreen

@Composable
fun NavigateApp() {
    // Создаем NavController
    val navController = rememberNavController()

    // Определяем граф навигации
    NavHost(
        navController = navController,
        startDestination = "main_screen" // Начальный экран
    ) {
        // Главный экран
        composable("main_screen") {
            MainScreen(navController)
        }

        // Детальный экран с параметром itemId
        composable("image_screen") {
            ImageScreen(navController)
        }
    }
}

