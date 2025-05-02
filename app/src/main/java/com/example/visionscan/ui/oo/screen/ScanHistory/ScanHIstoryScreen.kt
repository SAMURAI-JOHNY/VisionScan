package com.example.visionscan.ui.oo.screen.ScanHistory

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.visionscan.ui.oo.screen.ScanHistory.RecognitionItem
import com.example.visionscan.ui.oo.screen.ScanHistory.toFormattedDate // Добавляем этот импорт
import com.example.visionscan.ui.oo.screen.ScanHistory.HistoryViewModel

@Composable
fun ScanHistoryScreen(navController: NavController) {
    val viewModel: HistoryViewModel = viewModel()
    val history = viewModel.history.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HistoryHeader(navController)
        Spacer(modifier = Modifier.height(24.dp))
        HistoryContent(history, navController)
    }
}

@Composable
private fun HistoryHeader(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4EFFA),
                contentColor = Color(0xFF6F2DBD)
            ),
            modifier = Modifier.width(100.dp)
        ) {
            Text("Назад")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "История распознаваний",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6F2DBD)
            )
        )
    }
}

@Composable
private fun HistoryContent(history: List<RecognitionItem>, navController: NavController) {
    when {
        history.isEmpty() -> EmptyHistory()
        else -> HistoryList(history, navController)
    }
}

@Composable
private fun EmptyHistory() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "История распознаваний пока пуста",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Gray
            )
        )
    }
}

@Composable
private fun HistoryList(history: List<RecognitionItem>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(history) { item ->
            HistoryItemCard(item) {
                navController.navigate("scan_history/${item.id}")
            }
        }
    }
}

@Composable
private fun HistoryItemCard(item: RecognitionItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4EFFA)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.timestamp.toFormattedDate(),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Объектов найдено: ${item.detectedObjects.size}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6F2DBD)
                )
            )

            if (item.detectedObjects.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.detectedObjects.joinToString {
                        "${it.name} (${it.confidencePercent()}%)"
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                )
            }
        }
    }
}
