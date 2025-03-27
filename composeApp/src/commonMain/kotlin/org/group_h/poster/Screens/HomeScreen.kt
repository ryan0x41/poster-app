package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
                onClick = { /* handles new post */ },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Post", tint = Color.White)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Black) //black backgroundg
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
        backgroundColor = Color(0xFF212121), //dark grey container
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            //header for user
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp),
                    tint = Color.LightGray //brighter icon colour
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "SampleUsername",
                        fontWeight = FontWeight.Bold,
                        color = Color.White //white text
                    )
                    Text(
                        text = "2h ago",
                        fontSize = 12.sp,
                        color = Color.LightGray //light grey for secondary text
                    )
                }
            }

            //post content
            Text(
                text = post.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = Color.White, //white text
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = post.content,
                style = MaterialTheme.typography.body1,
                color = Color.White //white text
            )

            //media placeholders
            if (post.hasImage) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF424242), RoundedCornerShape(8.dp)), //darker grey
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
                        .background(Color(0xFF424242), RoundedCornerShape(8.dp)), //darker grey
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🎵 Song Recommendation",
                        color = Color.White //white text
                    )
                }
            }

            //like, comment, share buttons
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

@Composable
fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    iconColor: Color
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

val samplePosts = listOf(
    Post(
        title = "Beautiful night view!",
        content = "The city lights look amazing from up here.",
        hasImage = true
    ),
    Post(
        title = "New album released",
        content = "Just dropped my new EP, check it out!",
        hasSong = true
    ),
    Post(
        title = "Thought for the night",
        content = "The stars are especially bright tonight."
    )
)

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme(
        colors = darkColors()
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen()
        }
    }
}