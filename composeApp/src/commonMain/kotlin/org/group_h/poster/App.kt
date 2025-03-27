package org.group_h.poster

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.group_h.poster.Screens.HomeView
import org.group_h.poster.Screens.LoginPage
import org.group_h.poster.Screens.RegisterView
import org.group_h.poster.Screens.MessagesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }

    MaterialTheme {
        when (currentScreen) {
            "login" -> LoginPage(navigate = { screen -> currentScreen = screen })
            "home" -> HomeView(navigate = { screen -> currentScreen = screen })
            "createAccount" -> RegisterView(navigate = { screen -> currentScreen = screen })
            "messages" -> MessagesScreen(navigate = { currentScreen = it })
        }
    }
}

/*  might need to use this when implementing api
    or something similar
*/
//NavHost(navController, startDestination = "home") {
//
//    composable(
//        "profile/{userId}",
//        arguments = listOf(navArgument("userId") { type = NavType.StringType })
//    ) { backStackEntry ->
//        val userId = backStackEntry.arguments?.getString("userId") ?: ""
//        val currentUserId = "current_user_id_here"
//
//        //if its the current users profile
//        val isOwner = userId == currentUserId
//
//        //get user data
//        val user = viewModel.getUserById(userId)
//
//        ProfileScreen(
//            user = user,
//            isOwner = isOwner,
//            onEditProfile = { navController.navigate("editProfile") }
//        )
//    }
//}



@Preview
@Composable
fun AppPreview() {
    App()
}
