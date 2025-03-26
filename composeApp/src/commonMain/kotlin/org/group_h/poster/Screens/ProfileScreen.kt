package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

@Composable
fun ProfileScreen(user: User = sampleUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        //user info
        //centered with image placeholder on the right
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
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.joinDate,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (user.isCurrentUser) {
                    TextButton(onClick = { /* edit profile */ }) {
                        Text("edit profile?", color = Color.Blue)
                    }
                }
            }

            //profile image placeholder on right side
            //using box instead of image for now
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Image",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(user.posts) { post ->
                ProfilePostItem(post)
                Divider(
                    color = Color.LightGray,
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
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.content,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            //media placeholders
            if (post.hasImage) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFFEEEEEE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image Placeholder")
                }
            }

            if (post.hasSong) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎵 Song Recommendation")
                }
            }
        }
    }
}

//sample user infor and posts
val sampleUser = User(
    username = "TravelEnthusiast",
    joinDate = "Joined March 2023",
    isCurrentUser = true,
    posts = listOf(
        Post(
            title = "My trip to Bali!",
            content = "Just returned from an amazing vacation in Bali. The beaches were incredible!",
            hasImage = true
        ),
        Post(
            title = "Song of the week",
            content = "Currently obsessed with this new track by my favorite artist",
            hasSong = true
        ),
        Post(
            title = "Cooking adventure",
            content = "Tried making homemade pasta for the first time today. It was delicious!",
            hasImage = true
        )
    )
)

@Preview
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}