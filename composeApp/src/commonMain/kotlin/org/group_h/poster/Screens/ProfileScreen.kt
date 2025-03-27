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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

/*
    when calling ProfileScreen from other screens:
    for your own profile:
    ProfileScreen(user = currentUser, isOwner = true)

    for someone elses profile:
    ProfileScreen(user = otherUser, isOwner = false)
 */


@Composable
fun ProfileScreen(user: User = sampleUser, isOwner: Boolean = true) {
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
                    text = user.username,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White //white text
                )
                Text(
                    text = user.joinDate,
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
            items(user.posts) { post ->
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

@Composable
fun ProfilePostItem(post: Post) {
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
            if (post.hasImage) {
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

            if (post.hasSong) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFF424242)), //darker grey
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🎵 Song Recommendation",
                        color = Color.White //white text
                    )
                }
            }
        }
    }
}

//sample user posts
val sampleUser = User(
    username = "TravelEnthusiast",
    joinDate = "Joined March 2023",
    isCurrentUser = true,
    posts = listOf(
        Post(
            title = "Night view from the mountains",
            content = "The city lights look absolutely breathtaking from up here",
            hasImage = true
        ),
        Post(
            title = "New favorite album",
            content = "This jazz record has been on repeat all week",
            hasSong = true
        ),
        Post(
            title = "Homemade dinner",
            content = "Experimented with a new recipe tonight - turned out amazing!",
            hasImage = true
        )
    )
)

@Preview
@Composable
fun ProfileScreenPreview() {
    MaterialTheme(
        colors = darkColors()
    ) {
        ProfileScreen()
    }
}