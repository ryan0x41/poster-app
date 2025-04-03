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

// Dark mode colors
private val DarkBackground = Color(0xFF000000)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val DarkMessageBubbleOther = Color(0xFF424242)
private val DarkMessageBubbleUser = Color(0xFF2962FF)
private val DarkInputBackground = Color(0xFF2D2D2D)
private val SendButtonColor = Color(0xFF2962FF)

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
                title = { Text("Messages", style = MaterialTheme.typography.h6.copy(color = DarkText)) },
                navigationIcon = {
                    IconButton(onClick = { navigate("home") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go Back", tint = DarkText)
                    }
                },
                actions = {
                    IconButton(onClick = { /* search functionality */ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = DarkText)
                    }
                },
                backgroundColor = DarkSurface,
                contentColor = DarkText
            )
        },
        backgroundColor = DarkBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
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
                .background(DarkSurface)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // profile image icon
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier.size(40.dp),
                tint = DarkSecondaryText
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Text(text = lastMessage, fontSize = 14.sp, color = DarkSecondaryText)
            }

            // 3 dots menu
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Options", tint = DarkText)
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(DarkSurface)
                ) {
                    DropdownMenuItem(onClick = {
                        onHide() //hide the conversation
                        showMenu = false
                    }) {
                        Text("Hide", color = DarkText)
                    }
                    DropdownMenuItem(onClick = {
                        onDelete() //delete the conversation
                        showMenu = false
                    }) {
                        Text("Delete", color = DarkText)
                    }
                    DropdownMenuItem(onClick = {
                        onLeave() // leave the conversation
                        showMenu = false
                    }) {
                        Text("Leave Conversation", color = DarkText)
                    }
                }
            }
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
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
                title = { Text(chatName, color = DarkText) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Messages", tint = DarkText)
                    }
                },
                backgroundColor = DarkSurface,
                contentColor = DarkText
            )
        },
        backgroundColor = DarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
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
                    .background(DarkInputBackground)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    label = { Text("Type a message", color = DarkSecondaryText) },
                    modifier = Modifier
                        .weight(1f)
                        .background(DarkSurface, RoundedCornerShape(24.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = DarkText,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = DarkText,
                        focusedLabelColor = DarkSecondaryText,
                        unfocusedLabelColor = DarkSecondaryText
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
                        .background(SendButtonColor, RoundedCornerShape(50)) // Changed to SendButtonColor
                        .padding(8.dp)
                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send Message", tint = DarkText)
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
            .wrapContentWidth(
                align = if (isUserMessage) Alignment.End else Alignment.Start
            )
            .widthIn(max = 300.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isUserMessage) DarkMessageBubbleUser
                    else DarkMessageBubbleOther,
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = if (isUserMessage) text.removePrefix("USER:") else text,
                style = MaterialTheme.typography.body1,
                color = DarkText
            )
        }
    }
}

// Preview
@Preview
@Composable
fun MessagesScreenPreview() {
    MessagesListScreen(onChatSelected = {}, navigate = {})
}
