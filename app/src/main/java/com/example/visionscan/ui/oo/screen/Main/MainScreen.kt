// MainScreen.kt
package com.example.visionscan.ui.oo.screen.Main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.visionscan.ui.oo.Purple40
import com.example.visionscan.ui.oo.Purple80
import com.example.visionscan.ui.oo.White

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Заголовок
        Text(
            text = "VisionScan",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 48.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController.navigate("image_picker") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple40),
            ) {
                Text(text = "Выбрать изображение",
                     fontSize = 20.sp)
            }

            Button(
                onClick = { navController.navigate("scan_history") },
                colors = ButtonDefaults.buttonColors(contentColor = Purple40,
                                                     containerColor = White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "История распознований",
                    fontSize = 20.sp)
            }
        }
    }
}