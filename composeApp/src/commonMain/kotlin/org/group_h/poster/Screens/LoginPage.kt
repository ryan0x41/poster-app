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
import com.ryan.poster_app_api_wrapper.ApiClientSingleton

// TODO: throw this in a data class
private val DarkBackground = Color(0xFF000000)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val AccentBlue = Color(0xFF2962FF)
private val TextFieldBorder = Color(0xFF424242)

@Composable
fun LoginPage(navigate: (String) -> Unit) {
    // define spacing
    val spacing = 16.dp

    // declare username, password, and ui stuffs
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // our one instance of apiClient
    val apiClient = ApiClientSingleton

    // ui stuffs i wont explain
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
                    "login",
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Light),
                    color = DarkSecondaryText,
                    modifier = Modifier.offset(y = (-8).dp)
                )
                Spacer(modifier = Modifier.height(spacing))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Email or Username",
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
                            onClick = { passwordVisible = !passwordVisible }
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
                LoginCheckbox()
                Spacer(modifier = Modifier.height(spacing))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            try {
                                // when user clicks sign in, we try login, if ready, we do auth
                                // which saves our user information within the apiClient instance
                                // so we can getAuthenticatedUser later
                                apiClient.login(username.trim(), password)
                                apiClient.auth()
                                navigate("home")
                            } catch (e: Exception) {
                                // set an error message if login failed
                                errorMessage = "Login failed: ${e.message}"
                            } finally {
                                // for either result, we are no longer loading
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
                    Text("Sign In", style = MaterialTheme.typography.h6)
                }
                // if we have an error message
                errorMessage?.let { message ->
                    // show it in an alert
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
                    Text("Don't have an account yet?", color = DarkText)
                    Text(
                        text = "Sign Up",
                        color = AccentBlue,
                        modifier = Modifier.clickable { navigate("createAccount") }
                    )
                }
            }
        }
    }
}

// thanks josh
@Composable
fun LoginCheckbox() {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = AccentBlue,
                uncheckedColor = DarkSecondaryText
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Remember me", color = DarkText)
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage(navigate = {})
}
