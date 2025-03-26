package org.group_h.poster.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(user: User = sampleUser) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Header
        Text(
            text = "poster",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Divider(color = Color.LightGray, thickness = 1.dp)

        // User Info
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(user.username, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(user.joinDate, fontSize = 14.sp, color = Color.Gray)
            if (user.isCurrentUser) {
                TextButton(onClick = { /* Edit profile */ }) {
                    Text("edit profile?", color = Color.Blue)
                }
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

        // Posts List - THIS IS THE CRUCIAL PART
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = post.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.content,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

// Sample data - MAKE SURE THIS HAS ACTUAL POSTS
val sampleUser = User(
    username = "exampleUser",
    joinDate = "Joined Jan 2024",
    isCurrentUser = true,
    posts = listOf(  // Must have actual posts here
        Post("First Post", "Hello everyone!"),
        Post("Second Post", "This is my second post"),
        Post("Third Post", "Another day, another post")
    )
)