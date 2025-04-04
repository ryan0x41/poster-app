package org.group_h.poster.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.group_h.poster.Screens.HomeFeedView
import org.group_h.poster.Screens.LoginPage
import org.group_h.poster.Screens.MessagesScreen
import org.group_h.poster.Screens.NotificationScreen
import org.group_h.poster.Screens.PostScreen
import org.group_h.poster.Screens.ProfileScreen
import org.group_h.poster.ui.components.BottomNavigationBar
import org.group_h.poster.ui.components.TopBar

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Observe the current back stack entry to determine the current route.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "login"

    // Show bars on all main screens (i.e. hide them on login or createAccount)
    val showBars = currentRoute !in listOf("login", "createAccount")

    Scaffold(
        topBar = {
            if (showBars) {
                TopBar(onTabSelected = { tab ->
                    // For the top bar, if the user taps "messages" or "search", navigate accordingly.
                    if (tab == "messages" || tab == "search") {
                        navController.navigate(tab)
                    }
                })
            }
        },
        bottomBar = {
            if (showBars) {
                BottomNavigationBar(selectedTab = currentRoute) { tab ->
                    if (tab == "home") {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(tab) {
                            // For other tabs, pop up to "home" as a common root.
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                // In the login screen, if navigation to "home" is requested, remove "login" from the back stack.
                LoginPage(navigate = { route ->
                    if (route == "home") {
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(route)
                    }
                })
            }
            composable("home") {
                HomeFeedView(navigate = { route ->
                    navController.navigate(route)
                })
            }
            composable(
                route = "profile?userId={userId}",
                arguments = listOf(
                    navArgument("userId") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")
                ProfileScreen(
                    userId = if (userId.isNullOrEmpty()) null else userId,
                    navigate = { route ->
                        navController.navigate(route)
                    }
                )
            }
            composable("notifications") {
                NotificationScreen()
            }
            composable("post") {
                PostScreen()
            }
            composable("messages") {
                MessagesScreen(navigate = { route ->
                    navController.navigate(route)
                })
            }
            composable("search") {
                // Replace with your actual SearchScreen composable.
            }
        }
    }
}
