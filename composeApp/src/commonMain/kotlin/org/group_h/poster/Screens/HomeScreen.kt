package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(navigateToProfile: () -> Unit = {}) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle new post */ },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Post")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            items(samplePosts) { post ->
                PostItem(post = post)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // User header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "SampleUsername",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "2h ago",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Post content
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.content,
                style = MaterialTheme.typography.body1
            )

            // Media placeholders
            if (post.hasImage) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFFEEEEEE), RoundedCornerShape(8.dp)),
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
                        .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎵 Song Recommendation")
                }
            }

            // Action buttons - Now with better spacing and icon options
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Like button
                ActionButton(
                    icon = Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    onClick = { /* Like action */ }
                )

                // Comment button alternatives (choose one)
                ActionButton(
                    icon = Icons.Outlined.Send, // Best comment icon
                    contentDescription = "Comment",
                    onClick = { /* Comment action */ }
                )

                // Share button
                ActionButton(
                    icon = Icons.Outlined.Share,
                    contentDescription = "Share",
                    onClick = { /* Share action */ }
                )
            }
        }
    }
}

// Reusable action button component
@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 4.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.Gray // Set all content (icon+text) to gray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray // Explicit gray for icon
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = when (contentDescription) {
                    "Like" -> "Like"
                    "Comment" -> "Comment"
                    "Share" -> "Share"
                    else -> ""
                },
                fontSize = 12.sp,
                color = Color.Gray // Explicit gray for text
            )
        }
    }
}



// Keep your existing samplePosts list
val samplePosts = listOf(
    Post(
        title = "Beautiful day at the beach!",
        content = "Enjoying the sunny weather and ocean views.",
        hasImage = true
    ),
    Post(
        title = "New song recommendation",
        content = "Check out this amazing track I discovered!",
        hasSong = true
    ),
    Post(
        title = "Thought for the day",
        content = "The only way to do great work is to love what you do."
    ),
    Post(
        title = "Recipe share",
        content = "Made this delicious pasta dish today!",
        hasImage = true
    ),
    Post(
        title = "Weekend plans",
        content = "Looking forward to hiking this weekend!"
    )
)

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen()
        }
    }
}