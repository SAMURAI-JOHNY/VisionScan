package com.example.visionscan.ui.oo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ImageScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "VisioScan",
            style = TextStyle(fontSize = 50.sp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 250.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {navController.navigate("main_screen")},
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFF4EFFA),
                    containerColor = Color(0xFF6F2DBD)
                ),
                modifier = Modifier.size(width = 250.dp, height = 70.dp)
            ) {
                Text(
                    text = "На главную",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .width(200.dp)
                )
            }
        }
    }
}