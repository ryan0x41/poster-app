package org.group_h.poster.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    BottomNavigation(
        backgroundColor = Color.Black, // Black background
        contentColor = Color.White,    // White icons
        elevation = 8.dp              // Slight elevation for depth
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (selectedTab == "home") Color.White else Color.Gray
                )
            },
            selected = selectedTab == "home",
            onClick = {
                onTabSelected("home")
            },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = if (selectedTab == "notifications") Color.White else Color.Gray
                )
            },
            selected = selectedTab == "notifications",
            onClick = { onTabSelected("notifications") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Post",
                    tint = if (selectedTab == "post") Color.White else Color.Gray
                )
            },
            selected = selectedTab == "post",
            onClick = { onTabSelected("post") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = if (selectedTab == "profile") Color.White else Color.Gray
                )
            },
            selected = selectedTab == "profile",
            onClick = { onTabSelected("profile") },
            selectedContentColor = Color.White,
            unselectedContentColor = Color.Gray
        )
    }
}
