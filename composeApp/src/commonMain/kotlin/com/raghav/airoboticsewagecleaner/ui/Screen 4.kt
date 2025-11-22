package com.raghav.airoboticsewagecleaner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GpsMappingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Main map view placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            // Robot's live location pin
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Robot Location",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Lat: 34.0522, Lon: -118.2437",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }

            // Internal path overlay
            Text(
                text = "Internal Path: 25 meters North-East from entry point",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Share button
        Button(
            onClick = { /* Share live location */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Share Live Location")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GpsMappingScreenPreview() {
    GpsMappingScreen()
}
