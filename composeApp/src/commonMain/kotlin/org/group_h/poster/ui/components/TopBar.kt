package org.group_h.poster.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Poster") },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        actions = {
            IconButton(onClick = { /* handle search */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /* handle messages */ }) {
                Icon(Icons.Filled.Send, contentDescription = "Messages")
            }
        }
    )
}



