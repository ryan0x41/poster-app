package org.group_h.poster.Screens

import HomeFeedViewModel
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ryan.poster_app_api_wrapper.ApiClientSingleton
import com.ryan.poster_app_api_wrapper.HomeFeed
import com.ryan.poster_app_api_wrapper.HomeFeedPost
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeFeedView(
    navigate: (String) -> Unit = {},
    viewModel: HomeFeedViewModel = remember { HomeFeedViewModel() }
) {
    val homeFeed by viewModel.homeFeed.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { viewModel.loadHomeFeed(forceRefresh = true) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text("Refresh Feed")
                        }
                    }
                }
            }

            homeFeed?.posts?.let { posts ->
                items(posts) { post ->
                    PostItem(post = post, navigate = navigate)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// a clickable button with an image
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

// each HomeFeedPost from app-api-wrapper can be rendered using this function
@Composable
fun PostItem(post: HomeFeedPost, navigate: (String) -> Unit) {
    // grab the url of the profile image
    val profileUrl = post.userProfile.profileImageUrl

    // ui stuff, wont explain everything, thanks josh
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFF212121), // dark grey container
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)   ) {
            // user header with Coil3 profile image
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
                    if (!profileUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = profileUrl,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.LightGray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = post.userProfile.username,
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

            // media placeholder - keeping original implementation
            if (post.images.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFF424242), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image Placeholder", color = Color.White)
                }
            }

            // like comment and share buttons
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
fun HomeViewForPreview() {
    HomeFeedView(navigate = { /* no-op for preview */ })
}

@Preview
@Composable
fun HomeFeedViewPreview() {
    MaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeViewForPreview()
        }
    }
}
