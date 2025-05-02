package com.example.visionscan.ui.oo.screen.ImagePicker

import android.os.Environment
import android.os.Build
import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.visionscan.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerScreen(navController: NavController) {
    val context = LocalContext.current
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    val permissionsState = rememberMultiplePermissionsState(permissions)

    LaunchedEffect(permissionsState.allPermissionsGranted) {
        if (permissionsState.allPermissionsGranted) {
            showPermissionDialog = false
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && capturedImageUri != null) {
            val encodedUri = capturedImageUri.toString()
            navController.navigate("image_viewer/${Uri.encode(imageUri.toString())}")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val encodedUri = it.toString()
            navController.navigate("image_viewer/${Uri.encode(imageUri.toString())}")
        }
    }

    fun launchCamera() {
        val uri = createImageUri(context).also { capturedImageUri = it }
        cameraLauncher.launch(uri)
    }

    fun launchGallery() {
        galleryLauncher.launch("image/*")
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Требуются разрешения") },
            text = { Text("Для работы приложения необходимо предоставить разрешения") },
            confirmButton = {
                Button(onClick = {
                    permissionsState.launchMultiplePermissionRequest()
                    showPermissionDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showPermissionDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.select_image_source),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (permissionsState.allPermissionsGranted) {
                        launchCamera()
                    } else {
                        showPermissionDialog = true
                    }
                },
                modifier = Modifier.width(250.dp)
            ) {
                Text(text = stringResource(R.string.take_photo))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (permissionsState.allPermissionsGranted) {
                        launchGallery()
                    } else {
                        showPermissionDialog = true
                    }
                },
                modifier = Modifier.width(250.dp)
            ) {
                Text(text = stringResource(R.string.choose_from_gallery))
            }

            if (!permissionsState.allPermissionsGranted) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.permissions_required),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: context.cacheDir
    val imageFile = File.createTempFile(
        imageFileName,
        ".jpg",
        storageDir
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}