package com.example.visionscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.visionscan.ui.oo.VisionScanTheme
import com.example.visionscan.ui.oo.navigate.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisionScanTheme {
                AppNavigation()
            }
        }
    }
}