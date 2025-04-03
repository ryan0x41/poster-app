package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ryan.poster_app_api_wrapper.ApiClientSingleton
import com.ryan.poster_app_api_wrapper.FullPost
import com.ryan.poster_app_api_wrapper.FullUserProfile
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

/*
    we can call ProfileScreen from other screens, we can specify a userId
    this is used by the HomeFeedView, where each post has an associated author, we can go
    to an authors profile by navigating to profile screen and passing a userId

    the only instance where we leave this blank is when we are directing to our own profile page
    if so, this means we do a getAuthenticatedUser, grab the userId, then getFullUserProfile and render
    it just like we would originally

    fun shtuff
*/
@Composable
fun ProfileScreen(userId: String? = null, navigate: (String) -> Unit = {}) {
    // test/preview mode
    if (LocalInspectionMode.current) {
        ProfileScreenContent(user = sampleFullUserProfile, navigate = navigate)
        return
    }

    // declare a few things we will need
    var fullUserProfile by remember { mutableStateOf<FullUserProfile?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // use LaunchedEffect to load user profile async
    LaunchedEffect(Unit) {
        try {
            // check if we have a userId that was provided, if so this is our `user`
            fullUserProfile = if (userId != null) {
                ApiClientSingleton.getUserProfileById(userId).user
            } else {
                // there was no provided userId, do a getAuthenticatedUser
                //      - this only works after a login() and auth() on apiClient
                val authResponse = ApiClientSingleton.getAuthenticatedUser()
                val currentUserId = authResponse?.id ?: throw Exception("user not authenticated")
                ApiClientSingleton.getUserProfileById(currentUserId).user
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "error occurred loading profile"
        } finally {
            // after everything, we are no longer loading
            isLoading = false
        }
    }

    // if we are loading
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // display an indicator
            androidx.compose.material.CircularProgressIndicator()
        }
        return
    }

    // if we have an error message, display it in an alert
    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Error") },
            text = { Text(errorMessage!!) },
            confirmButton = {
                androidx.compose.material.Button(onClick = {
                    isLoading = true
                    errorMessage = null
                    coroutineScope.launch {
                        try {
                            // try again once alert dismissed
                            fullUserProfile = if (userId != null) {
                                ApiClientSingleton.getUserProfileById(userId).user
                            } else {
                                // same as before
                                val authResponse = ApiClientSingleton.getAuthenticatedUser()
                                val currentUserId = authResponse?.id ?: throw Exception("user not authenticated")
                                ApiClientSingleton.getUserProfileById(currentUserId).user
                            }
                        } catch (e: Exception) {
                            errorMessage = e.message ?: "error occurred loading profile"
                        } finally {
                            isLoading = false
                        }
                    }
                }) {
                    Text("Retry")
                }
            }
        )
        return
    }

    // if we have a fullUserProfile, lets render it
    fullUserProfile?.let { user ->
        ProfileScreenContent(user = user, navigate = navigate)
    }
}

// a composable function to render a FullUserProfile
@Composable
fun ProfileScreenContent(user: FullUserProfile, navigate: (String) -> Unit) {
    // ui stuff
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {
        // header, we make this clickable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                // thanks to AppNavigation we can navigate to profile with an extra param called userId
                // i know this is circular, since all posts on a profile is by the user being rendered on the profile
                // im not sure if i should do this or not i feel like i should
                .clickable { navigate("profile?userId=${user.id}") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ui user details
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    // TODO: date format
                    text = "Joined ${user.accountCreation}",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                // TODO: fix
                if (user.isAdmin == true) {
                    Text(
                        text = "Admin",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFA0A0)
                    )
                }
                // TODO: only render if isOwner
                TextButton(onClick = { /* edit profile */ }) {
                    Text("edit profile?", color = Color(0xFF1DA1F2))
                }
            }
            // coil3 - asyncimage, a pain to `implement`
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333)),
                contentAlignment = Alignment.Center
            ) {
                // make sure that our profileImageUrl actually exists
                if (!user.profileImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = user.profileImageUrl,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // if it does not just render an Icon
                    // TODO: fix crash onclick when this is null
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Image",
                        tint = Color.LightGray,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            // for each post
            items(user.posts) { post ->
                // render, pass post information
                ProfilePostItem(
                    post = post,
                    profileImageUrl = user.profileImageUrl,
                    username = user.username,
                    navigate = navigate
                )
            }
        }
    }
}

// renders a profile post item based on a FullPost, profileImageUrl and username
@Composable
fun ProfilePostItem(
    post: FullPost,
    profileImageUrl: String,
    username: String,
    navigate: (String) -> Unit
) {
    // ui shtuff
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFF212121),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // clickable, same thing as before
            Row(
                modifier = Modifier
                    .clickable { navigate("profile?userId=${post.author}") }
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // make sure profileImageUrl exists
                    if (!profileImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = profileImageUrl,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // if not, render default profile image icon instead
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.LightGray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                // gap
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = username,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = post.postDate,
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            }
            // post content
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.content,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            // UNTESTED: if images exist
            if (post.images.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF424242), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = post.images[0],
                        contentDescription = "Post Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            // like, comment, share buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    onClick = { /* like action */ },
                    iconColor = Color.LightGray
                )
                ActionButton(
                    icon = Icons.Outlined.Send,
                    contentDescription = "Comment",
                    onClick = { /* comment action */ },
                    iconColor = Color.LightGray
                )
                ActionButton(
                    icon = Icons.Outlined.Share,
                    contentDescription = "Share",
                    onClick = { /* share action */ },
                    iconColor = Color.LightGray
                )
            }
        }
    }
}

// sample data
val sampleFullUserProfile = FullUserProfile(
    id = "123",
    username = "exampleUsername",
    email = "example@example.com",
    profileImageUrl = "",
    accountCreation = -1,
    isAdmin = true,
    followers = emptyList(),
    following = emptyList(),
    posts = listOf(
        FullPost(
            postId = "p1",
            title = "post title",
            content = "yes",
            author = "no",
            postDate = "2003-08-06",
            likes = 120,
            likedBy = listOf("user1", "user2"),
            images = listOf("")
        ),
        FullPost(
            postId = "p2",
            title = "post 2",
            content = "more high quality content",
            author = "feedface",
            postDate = "1999",
            likes = 69,
            likedBy = listOf("user3"),
            images = emptyList()
        ),
        FullPost(
            postId = "p3",
            title = "yes",
            content = "i love content",
            author = "foodbeef",
            postDate = "2003-08-06",
            likes = 150,
            likedBy = listOf("user4", "user5"),
            images = listOf("")
        )
    ),
    listeningHistory = emptyList(),
    favouriteArtists = emptyList(),
    currentlyPlaying = null
)

@Preview
@Composable
fun ProfileScreenPreview() {
    MaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreenContent(
                user = sampleFullUserProfile,
                navigate = { /* no-op in preview */ }
            )
        }
    }
}

