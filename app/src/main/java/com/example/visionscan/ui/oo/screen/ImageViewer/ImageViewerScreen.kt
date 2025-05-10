package com.example.visionscan.ui.oo.screen.ImageViewer

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewerScreen(
    navController: NavController,
    imageUri: Uri
) {
    val context = LocalContext.current

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
            FloatingActionButton(
                onClick = {
                    val encodedUri = Uri.encode(imageUri.toString())
                    navController.navigate("analysis/$encodedUri")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.analyze)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri.toString().isNotBlank()) {
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
            } else {
                Text(
                    text = stringResource(R.string.image_not_found),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}