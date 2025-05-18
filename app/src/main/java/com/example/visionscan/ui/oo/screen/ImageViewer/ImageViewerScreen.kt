package com.example.visionscan.ui.oo.screen.ImageViewer


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.visionscan.R
import com.example.visionscan.data.database.AppDatabase
import com.example.visionscan.data.model.database.LabelResult
import com.example.visionscan.data.model.database.ObjectResult
import com.example.visionscan.data.model.database.RecognitionEntity
import com.example.visionscan.data.repository.RecognitionRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import com.example.visionscan.ui.oo.White
import com.example.visionscan.ui.oo.Purple40
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewerScreen(
    navController: NavController,
    imageUri: Uri
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val db = remember { AppDatabase.getDatabase(context) }
    val repository = remember { RecognitionRepository(db.recognitionDao()) }

    var labels by remember { mutableStateOf<List<String>>(emptyList()) }
    var objects by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var activeTab by remember { mutableStateOf(0) }
    var showResults by remember { mutableStateOf(false) }

    val labeler = remember {
        ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
    }

    val objectDetector = remember {
        ObjectDetection.getClient(
            ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableMultipleObjects()
                .enableClassification()
                .build()
        )
    }

    fun analyzeImage() {
        isLoading = true
        labels = emptyList()
        objects = emptyList()

        scope.launch {
            try {
                val image = InputImage.fromFilePath(context, imageUri)

                val (rawLabels, rawObjects) = withContext(Dispatchers.Default) {
                    val labelsDeferred = async { labeler.process(image).await() }
                    val objectsDeferred = async { objectDetector.process(image).await() }
                    Pair(labelsDeferred.await(), objectsDeferred.await())
                }

                // 2. Форматируем данные для UI
                labels = rawLabels.map { "${it.text} (${(it.confidence * 100).toInt()}%)" }
                objects = rawObjects.flatMap { obj ->
                    obj.labels.map { label ->
                        "${label.text ?: "Объект"} (${(label.confidence * 100).toInt()}%)"
                    }
                }

                withContext(Dispatchers.IO) {
                    val recognitionId = repository.insertRecognition(
                        RecognitionEntity(imageUri = imageUri)
                    )

                    // Сохраняем метки
                    rawLabels.forEach { label ->
                        repository.insertLabel(
                            LabelResult(
                                recognitionId = recognitionId, // Указываем ID
                                text = label.text,
                                confidence = label.confidence
                            )
                        )
                    }

                    // Сохраняем объекты
                    rawObjects.forEach { obj ->
                        obj.labels.forEach { label ->
                            repository.insertObject(
                                ObjectResult(
                                    recognitionId = recognitionId,
                                    text = label.text ?: "Объект",
                                    confidence = label.confidence
                                )
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                labels = listOf("Ошибка: ${e.localizedMessage}")
            } finally {
                isLoading = false
                showResults = true
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.image_preview)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { analyzeImage() },
                modifier = Modifier.padding(16.dp),
                containerColor = Purple40,
                contentColor = White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                ),
                icon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = White
                        )
                    } else {
                        Icon(
                            Icons.Default.Search,
                            stringResource(R.string.analyze),
                            tint = White
                        )
                    }
                },
                text = {
                    Text(
                        stringResource(R.string.analyze),
                        fontSize = 18.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(imageUri)
                        .build()
                ),
                contentDescription = stringResource(R.string.selected_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            if (showResults) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TabRow(selectedTabIndex = activeTab) {
                            Tab(
                                selected = activeTab == 0,
                                onClick = { activeTab = 0 },
                                text = { Text("Метки") }
                            )
                            Tab(
                                selected = activeTab == 1,
                                onClick = { activeTab = 1 },
                                text = { Text("Объекты") }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyColumn {
                            items(if (activeTab == 0) labels else objects) { item ->
                                Text(
                                    text = item,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}