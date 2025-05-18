package com.example.visionscan.ui.oo.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.visionscan.data.database.AppDatabase
import com.example.visionscan.data.model.database.RecognitionWithDetails
import com.example.visionscan.data.repository.RecognitionRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanHistoryScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { AppDatabase.getDatabase(context) }
    val repository = remember { RecognitionRepository(db.recognitionDao()) }

    val recognitions by repository.getAllRecognitionsWithDetails().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("История распознаваний") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(recognitions) { recognition ->
                RecognitionHistoryItem(
                    recognition = recognition,
                    onDelete = {
                        scope.launch {
                            repository.deleteRecognition(recognition.recognition.id)
                        }
                    },
                )
                Divider()
            }
        }
    }
}

@Composable
fun RecognitionHistoryItem(
    recognition: RecognitionWithDetails,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Превью изображения
            AsyncImage(
                model = recognition.recognition.imageUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Основные метки
            Text(
                text = "Метки: ${recognition.labels.take(3).joinToString { it.text }}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Основные объекты
            Text(
                text = "Объекты: ${recognition.objects.take(3).joinToString { it.text }}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Кнопка удаления
            IconButton(
                onClick = onDelete,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Delete, "Удалить")
            }
        }
    }
}