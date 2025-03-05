package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Notification(val id: Int, val message: String)

// placeholder notifications
val sampleNotifications = listOf(
    Notification(1, "exampleUser followed you"),
    Notification(2, "exampleUser messaged you"),
    Notification(3, "exampleUser commented on your post")
)

@Composable
fun NotificationScreen() {
    var notifications by remember { mutableStateOf(sampleNotifications) }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            // displays the notifications
            items(notifications, key = { it.id }) { notification ->
                NotificationItem(
                    notification = notification,
                    onRemove = { notifications = notifications.filter { it.id != notification.id } }
                )
            }

            // adding "clear all" button in the lazy column
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
                            color = Color.Black,
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // placeholder for the profile pic (not working currently)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Blue, shape = CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = notification.message,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )

            // remove notification button
            Text(
                text = "X",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onRemove() }
            )
        }
    }
}
