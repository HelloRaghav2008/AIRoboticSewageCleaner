package com.raghav.airoboticsewagecleaner.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardScreen(
    videoFrame: ImageBitmap? = null,
    onNavigateToChemicals: () -> Unit,
    onNavigateToGps: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        // Main content (Video feed and controls)
        Column(modifier = Modifier.weight(1f)) {
            // Primary View (Video Feed and HUD)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .background(Color.Black)
                    .clickable { onNavigateToGps() }
            ) {
                // Live video feed
                if (videoFrame != null) {
                    Image(
                        bitmap = videoFrame,
                        contentDescription = "Live Video Feed",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Placeholder text when no video
                    Text(
                        "Waiting for Robot Connection...",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // HUD Overlay
                HudOverlay()
            }

            // Control Hub
            ControlHub(modifier = Modifier.fillMaxWidth().weight(0.3f))
        }

        // Data Ticker
        DataTicker(modifier = Modifier.width(200.dp), onNavigateToChemicals = onNavigateToChemicals)
    }
}

@Composable
fun HudOverlay() {
    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text("GPS: Live", color = Color.White)
            Text("Battery: 98%", color = Color.White)
        }
        Column(modifier = Modifier.align(Alignment.TopEnd)) {
            Text("Time: 00:15:32", color = Color.White)
            Text("REC", color = Color.Red, fontWeight = FontWeight.Bold)
        }
        Text(
            "--- Path Breadcrumb ---",
            color = Color.Green,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ControlHub(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        // Left: Movement Joystick
        Joystick(title = "Movement", modifier = Modifier.weight(1f))

        // Center: Action Buttons
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Clean blockage */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Yellow,
                    contentColor = Color.Black
                )
            ) {
                Text("CLEAN BLOCKAGE")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                IconButton(onClick = { /* Toggle light */ }) {
                    Icon(Icons.Filled.Lightbulb, contentDescription = "Toggle Light")
                }
                IconButton(onClick = { /* Take snapshot */ }) {
                    Icon(Icons.Filled.Camera, contentDescription = "Take Snapshot")
                }
            }
        }

        // Right: Tools Joystick
        Joystick(title = "Camera/Tools", modifier = Modifier.weight(1f))
    }
}

@Composable
fun Joystick(title: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            // Placeholder for actual joystick
            Box(modifier = Modifier.size(100.dp).background(Color.LightGray))
        }
    }
}

@Composable
fun DataTicker(modifier: Modifier = Modifier, onNavigateToChemicals: () -> Unit) {
    Column(modifier = modifier.padding(8.dp).fillMaxHeight()) {
        // Chemicals
        Column(modifier = Modifier.clickable { onNavigateToChemicals() }) {
            Text(
                "CHEMICALS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("H₂S: 15 ppm (Warning)", color = Color.Yellow)
            Text("Methane: 5% LEL (Danger)", color = Color.Red)
            Text("Ammonia: 3 ppm (OK)", color = Color.Green)
        }
        Spacer(modifier = Modifier.height(24.dp))

        // AI Alerts
        Text(
            "AI ALERTS",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("[10:32 AM] Alert: Suspicious Object Detected.")
        Text("[10:31 AM] Alert: Potential crack in pipe wall.")
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(onNavigateToChemicals = {}, onNavigateToGps = {})
}
