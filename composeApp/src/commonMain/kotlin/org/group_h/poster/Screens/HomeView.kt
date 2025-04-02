package org.group_h.poster.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ryan.poster_app_api_wrapper.ApiClientSingleton
import com.ryan.poster_app_api_wrapper.FullUserProfileResponse
import org.group_h.poster.ui.components.BottomNavigationBar
import org.group_h.poster.ui.components.TopBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeView(navigate: (String) -> Unit) {
    var selectedBotTab by remember { mutableStateOf("home") }
    val apiClient = ApiClientSingleton
    var user by remember { mutableStateOf<FullUserProfileResponse?>(null) }

    LaunchedEffect(Unit) {
        val authenticatedUser = apiClient.getAuthenticatedUser()
        user = authenticatedUser?.id?.let { apiClient.getUserProfileById(it) }
    }

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
                "profile" -> ProfileScreen(
                    isOwner = true,
                    user = user
                )
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
