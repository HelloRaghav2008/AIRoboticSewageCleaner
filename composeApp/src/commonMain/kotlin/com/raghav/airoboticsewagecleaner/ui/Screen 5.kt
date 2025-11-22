package com.raghav.airoboticsewagecleaner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MissionLogScreen(selectedMission: Mission?, onMissionSelected: (Mission) -> Unit) {
    if (selectedMission == null) {
        MissionListScreen(onMissionSelected = onMissionSelected)
    } else {
        MissionDetailsScreen(mission = selectedMission)
    }
}

@Composable
fun MissionListScreen(onMissionSelected: (Mission) -> Unit) {
    val missions = remember {
        listOf(
            Mission("1", "10 Nov 2025 - 10:30 AM", "45 minutes", "150 meters"),
            Mission("2", "09 Nov 2025 - 02:15 PM", "30 minutes", "100 meters")
        )
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(missions) {
            Card(modifier = Modifier.fillMaxWidth().clickable { onMissionSelected(it) }) {
                Text(
                    text = "Mission: ${it.date}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun MissionDetailsScreen(mission: Mission) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Summary
        Text(
            "Summary",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Duration: ${mission.duration}", style = MaterialTheme.typography.bodyLarge)
            Text("Distance: ${mission.distance}", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map Placeholder
        Text("Map", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.LightGray))

        Spacer(modifier = Modifier.height(16.dp))

        // Video Player Placeholder
        Text(
            "Video Recording",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.Black))

        Spacer(modifier = Modifier.height(16.dp))

        // Logs
        Text("Logs", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        LazyColumn {
            item { Text("Chemical Warning: Methane level high at 10:35 AM") }
            item { Text("AI Alert: Crack detected at 10:40 AM") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MissionLogScreenPreview() {
    MissionLogScreen(null, {})
}