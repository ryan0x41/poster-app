package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryan.poster_app_api_wrapper.AuthenticatedUser
import com.ryan.poster_app_api_wrapper.ApiClientSingleton
import com.ryan.poster_app_api_wrapper.FullPost
import com.ryan.poster_app_api_wrapper.FullUserProfileResponse
import org.jetbrains.compose.ui.tooling.preview.Preview

/*
    when calling ProfileScreen from other screens:
    for your own profile:
    ProfileScreen(user = currentUser, isOwner = true)

    for someone elses profile:
    ProfileScreen(user = otherUser, isOwner = false)
 */

@Composable
fun ProfileScreen(user: FullUserProfileResponse?, isOwner: Boolean = true) {
    if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading...", color = Color.White)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black) // black background
                .padding(horizontal = 16.dp)
        ) {
            //user info section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //user details
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = user.user.username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White //white text
                    )
                    Text(
                        text = user.user.accountCreation.toString(),
                        fontSize = 14.sp,
                        color = Color.LightGray // light grey for secondary text
                    )
                    if (isOwner) {//only pops up if youre on your own profile page
                        TextButton(onClick = { /* edit profile */ }) {
                            Text(
                                "edit profile?",
                                color = Color(0xFF1DA1F2) // edit profile blue
                            )
                        }
                    }
                }

                //profile image placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF333333)), //dark grey circle for profile image
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile Image",
                        tint = Color.LightGray, // light grey icon
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            Divider(color = Color(0xFF333333), thickness = 1.dp) //dark grey divider

            //list of posts
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(user.user.posts) { post ->
                    ProfilePostItem(post)
                    Divider(
                        color = Color(0xFF333333),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfilePostItem(post: FullPost) {
    Card(
        elevation = 4.dp,
        backgroundColor = Color(0xFF212121), //dark grey card
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //post content
            Text(
                text = post.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, //white text
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.content,
                fontSize = 16.sp,
                color = Color.White, //white text
                modifier = Modifier.padding(bottom = 8.dp)
            )

            //media placeholders
            if (post.images.size > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF424242)), //darker grey for media background
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Image Placeholder",
                        color = Color.White //white text
                    )
                }
            }

//            if (post.hasSong) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(60.dp)
//                        .background(Color(0xFF424242)), //darker grey
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        "🎵 Song Recommendation",
//                        color = Color.White //white text
//                    )
//                }
//            }
        }
    }
}

private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val AccentBlue = Color(0xFF2962FF)
private val TextFieldBorder = Color(0xFF424242)

@Preview
@Composable
fun ProfileScreenPreview() {
    MaterialTheme(colors = darkColors()) {
        var user by remember { mutableStateOf<FullUserProfileResponse?>(null) }
        // load user async
        LaunchedEffect(Unit) {
            val authenticatedUser: AuthenticatedUser? = ApiClientSingleton.getAuthenticatedUser()
            user = authenticatedUser?.id?.let { ApiClientSingleton.getUserProfileById(it) }
        }
        if (user != null) {
            ProfileScreen(user = user, isOwner = true)
        } else {
            // show loading while user being fetched
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...", color = DarkText)
            }
        }
    }
}
