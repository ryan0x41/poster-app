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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

// Dark theme colors
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val AccentBlue = Color(0xFF2962FF)
private val TextFieldBorder = Color(0xFF424242)

@Composable
fun LoginPage(navigate: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    "poster",
                    style = MaterialTheme.typography.h4,
                    color = DarkText,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Username/Email text field
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text("Username/Email", style = MaterialTheme.typography.h6.copy(color = DarkText))
                    BasicTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = DarkText),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (username.isEmpty()) {
                                    Text(text = " exampleUsername123", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password field with text-based visibility toggle
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Password", style = MaterialTheme.typography.h6.copy(color = DarkText))
                        TextButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.padding(end = 8.dp)
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
                            .padding(horizontal = 12.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = DarkText),
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
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

                Spacer(modifier = Modifier.height(0.dp))
                LoginCheckbox()

                Spacer(modifier = Modifier.height(24.dp))

                // Login button
                Button(
                    onClick = { navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AccentBlue,
                        contentColor = DarkText
                    )
                ) {
                    Text("Sign In", style = MaterialTheme.typography.h6)
                }

                // Sign up link
                Spacer(modifier = Modifier.height(0.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Don't have an account yet? ", color = DarkText)
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

@Composable
fun LoginCheckbox() {
    var checked by remember { mutableStateOf(true) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = AccentBlue,
                    uncheckedColor = DarkSecondaryText
                )
            )
            Text("Remember me", color = DarkText)
        }
        Text(
            "Forgot Password?",
            color = AccentBlue,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.clickable { /* handle forgot password click */ }
        )
    }
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage(navigate = {})
}