package org.group_h.poster.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.ryan.poster_app_api_wrapper.ApiClient
import kotlinx.coroutines.launch

private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val AccentBlue = Color(0xFF2962FF)
private val TextFieldBorder = Color(0xFF424242)

@Composable
fun RegisterView(navigate: (String) -> Unit) {
    val spacing = 16.dp
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val apiClient = remember { ApiClient() }

    MaterialTheme(
        colors = darkColors(
            primary = AccentBlue,
            surface = DarkSurface,
            background = DarkBackground,
            onPrimary = DarkText,
            onSurface = DarkText,
            onBackground = DarkText
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(spacing),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "poster-social",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                    color = DarkText,
                    modifier = Modifier.offset(y = (-20).dp)
                )
                Text(
                    "register",
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Light),
                    color = DarkSecondaryText,
                    modifier = Modifier.offset(y = (-8).dp)
                )
                Spacer(modifier = Modifier.height(spacing))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Username",
                        style = MaterialTheme.typography.h6.copy(color = DarkText, fontSize = 14.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = spacing),
                        textStyle = TextStyle(fontSize = 20.sp, color = DarkText),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (username.isEmpty()) {
                                    Text(text = "exampleUsername", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Email",
                        style = MaterialTheme.typography.h6.copy(color = DarkText, fontSize = 14.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = spacing),
                        textStyle = TextStyle(fontSize = 20.sp, color = DarkText),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (email.isEmpty()) {
                                    Text(text = "example@mail.com", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Password",
                            style = MaterialTheme.typography.h6.copy(color = DarkText, fontSize = 14.sp)
                        )
                        TextButton(
                            onClick = { passwordVisible = !passwordVisible },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = if (passwordVisible) "HIDE" else "SHOW",
                                color = AccentBlue,
                                fontSize = 14.sp
                            )
                        }
                    }
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = spacing),
                        textStyle = TextStyle(fontSize = 20.sp, color = DarkText),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (password.isEmpty()) {
                                    Text(text = "●●●●●●●●●●●", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            try {
                                apiClient.register(username.trim(), email.trim(), password)
                                successMessage = "Registration successful. Please log in."
                            } catch (e: Exception) {
                                errorMessage = "Registration failed: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = spacing),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AccentBlue,
                        contentColor = DarkText
                    )
                ) {
                    Text("Register", style = MaterialTheme.typography.h6)
                }
                successMessage?.let { message ->
                    AlertDialog(
                        onDismissRequest = { /* prevents dismiss outside touch */ },
                        title = { Text("Success") },
                        text = { Text(message) },
                        confirmButton = {
                            Button(
                                onClick = {
                                    successMessage = null
                                    navigate("login")
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = AccentBlue)
                            ) {
                                Text("OK", color = DarkText)
                            }
                        }
                    )
                }
                errorMessage?.let { message ->
                    AlertDialog(
                        onDismissRequest = { errorMessage = null },
                        title = { Text("Error") },
                        text = { Text(message) },
                        confirmButton = {
                            Button(
                                onClick = { errorMessage = null },
                                colors = ButtonDefaults.buttonColors(backgroundColor = AccentBlue)
                            ) {
                                Text("OK", color = DarkText)
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(spacing))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Already have an account?", color = DarkText)
                    Text(
                        text = "Sign In",
                        color = AccentBlue,
                        modifier = Modifier.clickable { navigate("login") }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterViewPreview() {
    RegisterView(navigate = {})
}
