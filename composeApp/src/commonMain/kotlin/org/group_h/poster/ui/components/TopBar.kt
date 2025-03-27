package org.group_h.poster.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TopBar(onTabSelected: (String) -> Unit) {
    TopAppBar(
        title = {
            Text(
                "poster",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        },
        backgroundColor = Color.Black, // Black background
        contentColor = Color.White,    // White icons/text
        actions = {
            IconButton(onClick = { onTabSelected("search") }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = { onTabSelected("messages") }) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Messages",
                    tint = Color.White
                )
            }
        }
    )
}