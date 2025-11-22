package com.raghav.airoboticsewagecleaner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

enum class Screen(val title: String) {
    ROBOT_LIST("AI Robotic Sewage Cleaner"),
    PRE_MISSION_CHECKLIST("Pre-Mission Checklist"),
    DASHBOARD("Mission Control"),
    CHEMICAL_ANALYSIS("Live Chemical Analysis"),
    GPS_MAPPING("GPS & Mapping"),
    MISSION_LOG("Past Mission Reports")
}

data class Robot(val name: String, val status: String)
data class Mission(val id: String, val date: String, val duration: String, val distance: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(videoFrame: ImageBitmap? = null) {
    var currentScreen by remember { mutableStateOf(Screen.ROBOT_LIST) }
    var selectedRobot by remember { mutableStateOf<Robot?>(null) }
    var selectedMission by remember { mutableStateOf<Mission?>(null) }
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title =
                        if (currentScreen == Screen.MISSION_LOG && selectedMission != null) {
                            "Mission Details"
                        } else {
                            currentScreen.title
                        }
                    Text(title)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    if (currentScreen != Screen.ROBOT_LIST || selectedMission != null) {
                        IconButton(onClick = {
                            if (selectedMission != null) {
                                selectedMission = null
                            } else {
                                currentScreen = when (currentScreen) {
                                    Screen.PRE_MISSION_CHECKLIST -> Screen.ROBOT_LIST
                                    Screen.DASHBOARD -> Screen.PRE_MISSION_CHECKLIST
                                    Screen.CHEMICAL_ANALYSIS -> Screen.DASHBOARD
                                    Screen.GPS_MAPPING -> Screen.DASHBOARD
                                    Screen.MISSION_LOG -> Screen.ROBOT_LIST
                                    else -> Screen.ROBOT_LIST
                                }
                            }
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                actions = {
                    if (currentScreen == Screen.ROBOT_LIST) {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }) {
                            DropdownMenuItem(text = { Text("Past Missions") }, onClick = {
                                currentScreen = Screen.MISSION_LOG
                                menuExpanded = false
                            })
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (currentScreen) {
                Screen.ROBOT_LIST -> RobotListScreen(
                    onConnect = { robot ->
                        selectedRobot = robot
                        currentScreen = Screen.PRE_MISSION_CHECKLIST
                    },
                )

                Screen.PRE_MISSION_CHECKLIST -> PreMissionChecklistScreen(
                    robot = selectedRobot!!,
                    onStartMission = { currentScreen = Screen.DASHBOARD }
                )

                Screen.DASHBOARD -> DashboardScreen(
                    videoFrame = videoFrame,
                    onNavigateToChemicals = { currentScreen = Screen.CHEMICAL_ANALYSIS },
                    onNavigateToGps = { currentScreen = Screen.GPS_MAPPING }
                )

                Screen.CHEMICAL_ANALYSIS -> ChemicalAnalysisScreen()
                Screen.GPS_MAPPING -> GpsMappingScreen()
                Screen.MISSION_LOG -> MissionLogScreen(
                    selectedMission = selectedMission,
                    onMissionSelected = { mission -> selectedMission = mission }
                )
            }
        }
    }
}

@Composable
fun RobotListScreen(onConnect: (Robot) -> Unit) {
    val robots = remember {
        listOf(
            Robot("RC-Unit-01", "Online"),
            Robot("RC-Unit-02", "Offline"),
            Robot("RC-Unit-03", "Connecting...")
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(robots) { robot ->
            RobotItem(robot = robot, onConnect = { onConnect(robot) })
        }
    }
}

@Composable
fun RobotItem(robot: Robot, onConnect: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = robot.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Status: ${robot.status}", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onConnect, enabled = robot.status == "Online") {
                Text("Connect")
            }
        }
    }
}

@Composable
fun PreMissionChecklistScreen(robot: Robot, onStartMission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Connected to: ${robot.name}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        ChecklistItem(label = "Robot Battery", value = "100%")
        ChecklistItem(label = "App/Robot Link", value = "Strong")
        ChecklistItem(label = "GPS Status", value = "Locked (Acquiring...)")
        ChecklistItem(label = "Sensor Status", value = "All Green (Chemical, Camera, GPS)")
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onStartMission, modifier = Modifier.fillMaxWidth()) {
            Text("Start Mission")
        }
    }
}

@Composable
fun ChecklistItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}