package org.group_h.poster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginPage(navController) }
                composable("home") { HomeScreen() }
                composable("createAccount") { CreateAccountScreen() }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val navController = rememberNavController()
    LoginPage(navController = navController)
}