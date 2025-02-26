package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessagesScreen(navigate: (String) -> Unit) {
    var selectedConversation by remember { mutableStateOf<String?>(null) }

    if (selectedConversation != null) {
        ChatScreen(
            chatName = selectedConversation!!,
            onBack = { selectedConversation = null }
        )
    } else {
        MessagesListScreen(onChatSelected = { selectedConversation = it }, navigate = navigate)
    }
}

// conversation template
val sampleConversations = mutableMapOf<String, MutableList<String>>(
    "John" to mutableListOf("Hey", "How are you?", "Whats up pal"),
    "Jason" to mutableListOf("Hello", "Test Test", "Blah Blah"),
    "Pablo" to mutableListOf("Hola", "Hola", "Cartelo"),
    "Rey" to mutableListOf("Hi", "what is up", "Mysterio"),
    "Michael" to mutableListOf("Yah", "Wagwan", "No Pat")
)

// display all the conversation
@Composable
fun MessagesListScreen(onChatSelected: (String) -> Unit, navigate: (String) -> Unit) {
    // hidden conversations
    val hiddenConversations = remember { mutableStateListOf<String>() }
    // delete conversations
    val deletedConversations = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages", style = MaterialTheme.typography.h6) },
                navigationIcon = {
                    IconButton(onClick = { navigate("home") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* search functionality */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(sampleConversations.keys.toList()) { name ->
                if (!hiddenConversations.contains(name) && !deletedConversations.contains(name)) {
                    MessageItem(
                        name = name,
                        onClick = { onChatSelected(name) },
                        onHide = { hiddenConversations.add(name) }, // hides the conversation
                        onDelete = { deletedConversations.add(name) }, // deletes the conversation
                        onLeave = { /* Handle leave conversation */ }
                    )
                }
            }
        }
    }
}

// conversation preview items
@Composable
fun MessageItem(
    name: String,
    onClick: () -> Unit,
    onHide: () -> Unit,
    onDelete: () -> Unit,
    onLeave: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val lastMessage = sampleConversations[name]?.lastOrNull() ?: "No messages yet"

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // profile image icon
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(40.dp),
                tint = Color.Gray
            )

            Spacer(modifier = Modifier.width(12.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = lastMessage, fontSize = 14.sp, color = Color.Gray)
            }

            // 3 dots menu
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Options")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        onHide() //hide the conversation
                        showMenu = false
                    }) {
                        Text("Hide")
                    }
                    DropdownMenuItem(onClick = {
                        onDelete() //delete the conversation
                        showMenu = false
                    }) {
                        Text("Delete")
                    }
                    DropdownMenuItem(onClick = {
                        onLeave() // leave the conversation
                        showMenu = false
                    }) {
                        Text("Leave Conversation")
                    }
                }
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

// chat screen with reply functionality
@Composable
fun ChatScreen(chatName: String, onBack: () -> Unit) {
    val messages = remember { mutableStateListOf<String>() }
    messages.addAll(sampleConversations[chatName] ?: listOf("No messages yet"))

    var newMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chatName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Messages")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // displays messages with most recent message at the bottom of the screen
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp),
                reverseLayout = false
            ) {
                items(messages) { message ->

                    ChatBubble(
                        text = message,
                        isUserMessage = message.startsWith("USER:") // checks if message is a user message
                    )
                }
            }

            // input field for bottom of screen
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray) // grey background
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    label = { Text("Type a message") },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, RoundedCornerShape(24.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (newMessage.isNotEmpty()) {
                            messages.add("USER:$newMessage")
                            sampleConversations[chatName] = messages.toMutableList()
                            newMessage = ""
                        }
                    },
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(50))
                        .padding(8.dp)
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send Message", tint = Color.Black)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUserMessage: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                if (isUserMessage) Color(0xFF2196F3) // blue background for user messages
                else Color(0xFFE0E0E0), // grey background for other messages
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        contentAlignment = if (isUserMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = if (isUserMessage) text.removePrefix("USER:") else text,
            style = MaterialTheme.typography.body1,
            color = if (isUserMessage) Color.White else Color.Black
        )
    }
}
// Preview
@Preview
@Composable
fun MessagesScreenPreview() {
    MessagesListScreen(onChatSelected = {}, navigate = {})
}