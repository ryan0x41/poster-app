package org.group_h.poster

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem
import org.group_h.poster.Screens.HomeView
import org.group_h.poster.Screens.LoginPage
import org.group_h.poster.Screens.RegisterView
import org.group_h.poster.Screens.MessagesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }

    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        when (currentScreen) {
            "login" -> LoginPage(navigate = { screen -> currentScreen = screen })
            "home" -> HomeView(navigate = { screen -> currentScreen = screen })
            "createAccount" -> RegisterView(navigate = { screen -> currentScreen = screen })
            "messages" -> MessagesScreen(navigate = { currentScreen = it })
        }
    }
}

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
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
