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
fun HomeView() {
    var selectedTab by remember { mutableStateOf("home") }

    Column(modifier = Modifier.fillMaxSize()) {

        //top navigation
        TopBar()

        // Content based on the selected tab
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                "home" -> HomeScreen()
                "notifications" -> NotificationScreen()
                "post" -> PostScreen()
                "profile" -> ProfileScreen()

            }
        }


        // bottom navigation
        BottomNavigationBar(selectedTab) { tab -> selectedTab = tab }
    }
}

//@Composable
//fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
//    BottomNavigation(
//        backgroundColor = Color.White,
//        contentColor = Color.Black
//    ) {
//        BottomNavigationItem(
//            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
//            selected = selectedTab == "home",
//            onClick = { onTabSelected("home") }
//        )
//
//        BottomNavigationItem(
//            icon = { Icon(Icons.Filled.Add, contentDescription = "Search") },
//            selected = selectedTab == "post",
//            onClick = { onTabSelected("post") }
//        )
//
//        BottomNavigationItem(
//            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Messages") },
//            selected = selectedTab == "notifications",
//            onClick = { onTabSelected("notifications") }
//        )
//
//        BottomNavigationItem(
//            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
//            selected = selectedTab == "profile",
//            onClick = { onTabSelected("profile") }
//        )
//
//
//    }
//}
//
//@Composable
//fun HomeScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Home Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//@Composable
//fun PostScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Post Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//@Composable
//fun NotificationScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Notification Screen", style = MaterialTheme.typography.h4)
//    }
//}
//
//@Composable
//fun ProfileScreen() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text("Profile Screen", style = MaterialTheme.typography.h4)
//    }
//}




// Preview
@Preview
@Composable
fun HomeViewPreview() {
    HomeView()
}