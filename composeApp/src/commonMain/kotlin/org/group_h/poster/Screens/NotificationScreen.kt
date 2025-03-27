package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Notification(val id: Int, val message: String)

val sampleNotifications = listOf(
    Notification(1, "exampleUser followed you"),
    Notification(2, "exampleUser messaged you"),
    Notification(3, "exampleUser commented on your post"),
    Notification(4, "newUser liked your photo"),
    Notification(5, "friendUser shared your post")
)

@Composable
fun NotificationScreen() {
    var notifications by remember { mutableStateOf(sampleNotifications) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Black background
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(notifications, key = { it.id }) { notification ->
                NotificationItem(
                    notification = notification,
                    onRemove = { notifications = notifications.filter { it.id != notification.id } }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (notifications.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { notifications = emptyList() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Clear All",
                            fontSize = 16.sp,
                            color = Color(0xFF1DA1F2), // Twitter-like blue
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFF212121), // Dark gray card
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333)), // Dark gray circle
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.LightGray, // Light gray icon
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Notification message
            Text(
                text = notification.message,
                fontSize = 16.sp,
                color = Color.White, // White text
                modifier = Modifier.weight(1f)
            )

            // Close button
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.LightGray // Light gray icon
                )
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    MaterialTheme(
        colors = darkColors() // Dark theme preview
    ) {
        NotificationScreen()
    }
}