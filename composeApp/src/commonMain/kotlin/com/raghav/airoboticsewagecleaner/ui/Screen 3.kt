package com.raghav.airoboticsewagecleaner.ui

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ChemicalReading(val name: String, val reading: String, val status: SafetyStatus)
enum class SafetyStatus(val color: Color, val label: String) {
    SAFE(Color.Green, "Safe"),
    CAUTION(Color.Yellow, "Caution"),
    DANGER(Color.Red, "Danger")
}

@Composable
fun ChemicalAnalysisScreen() {
    val chemicalReadings = listOf(
        ChemicalReading("Methane", "5% LEL", SafetyStatus.DANGER),
        ChemicalReading("Hydrogen Sulfide", "15 ppm", SafetyStatus.CAUTION),
        ChemicalReading("Ammonia", "3 ppm", SafetyStatus.SAFE)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(chemicalReadings) { chemical ->
                ChemicalItem(chemical = chemical)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Export data */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Export Chemical Data")
        }
    }
}

@Composable
fun ChemicalItem(chemical: ChemicalReading) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    chemical.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(chemical.reading, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Status: ", style = MaterialTheme.typography.bodyMedium)
                Box(modifier = Modifier.weight(1f).height(20.dp).background(chemical.status.color))
                Text(
                    chemical.status.label,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = chemical.status.color
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Live Graph:", style = MaterialTheme.typography.bodyMedium)
            // Placeholder for the graph
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChemicalAnalysisScreenPreview() {
    ChemicalAnalysisScreen()
}
