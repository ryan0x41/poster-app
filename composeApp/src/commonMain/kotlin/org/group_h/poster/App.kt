package org.group_h.poster

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.group_h.poster.Screens.HomeView
import org.group_h.poster.Screens.RegisterView
import org.group_h.poster.Screens.LoginPage
import org.jetbrains.compose.ui.tooling.preview.Preview

//@Composable
//fun App() {
//
//    MaterialTheme {
//        // reference our login page, load this first
//        LoginPage(navigate = {})
//    }
//}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }

    MaterialTheme {
        when (currentScreen) {
            "login" -> LoginPage(navigate = { screen -> currentScreen = screen })
            "home" -> HomeView()
            "createAccount" -> RegisterView(navigate = { screen -> currentScreen = screen })
        }
    }
}


@Preview
@Composable
fun AppPreview() {
    App()
}
