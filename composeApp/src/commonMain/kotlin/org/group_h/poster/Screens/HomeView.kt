package org.group_h.poster.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.group_h.poster.ui.components.BottomNavigationBar
import org.group_h.poster.ui.components.TopBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeView(navigate: (String) -> Unit) {
    var selectedBotTab by remember { mutableStateOf("home") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar that triggers navigation when messages or search is pressed
        TopBar(onTabSelected = { tab ->
            // If the user taps "messages" or "search", navigate away
            if (tab == "messages" || tab == "search") {
                navigate(tab)
            }
        })

        // Content area controlled by bottom navigation selection
        Box(modifier = Modifier.weight(1f)) {
            when (selectedBotTab) {
                "home" -> HomeScreen()
                "notifications" -> NotificationScreen()
                "post" -> PostScreen()
                "profile" -> ProfileScreen()
            }
        }

        // Bottom Navigation (within the home flow)
        BottomNavigationBar(selectedTab = selectedBotTab) { tab ->
            selectedBotTab = tab
        }
    }
}

// Preview
@Preview
@Composable
fun HomeViewPreview() {
    HomeView(navigate = {})
}