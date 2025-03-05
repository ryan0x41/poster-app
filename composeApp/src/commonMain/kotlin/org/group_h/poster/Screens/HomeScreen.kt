package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// post variables
data class Post(
    val title: String,
    val content: String,
    val hasImage: Boolean = false,
    val hasSong: Boolean = false
)

// post placeholders
val samplePosts = listOf(
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "Lorem ipsum",
        hasImage = true,
        hasSong = true
    ),
    Post(
        title = "lorem ipsum",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        hasImage = true,
        hasSong = true
    ),
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "lorem ipsum"
    ),
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "lorem ipsum"
    ),
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "lorem ipsum"
    ),
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "Lorem ipsum",
        hasImage = true,
        hasSong = true
    ),
    Post(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        content = "lorem ipsum"
    )
)

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(samplePosts) { post ->
            PostItem(post)
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
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.content, style = MaterialTheme.typography.body2)

            if (post.hasImage) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                ) {
                    Text("Image Placeholder", modifier = Modifier.align(Alignment.Center))
                }
            }

            if (post.hasSong) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                ) {
                    Text("Recommended Song Placeholder", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

