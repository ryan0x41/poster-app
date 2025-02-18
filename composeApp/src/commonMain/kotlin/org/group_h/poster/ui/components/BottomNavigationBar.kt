package org.group_h.poster.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = selectedTab == "home",
            onClick = { onTabSelected("home") }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Post") },
            selected = selectedTab == "post",
            onClick = { onTabSelected("post") }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notifications") },
            selected = selectedTab == "notifications",
            onClick = { onTabSelected("notifications") }
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            selected = selectedTab == "profile",
            onClick = { onTabSelected("profile") }
        )
    }
}