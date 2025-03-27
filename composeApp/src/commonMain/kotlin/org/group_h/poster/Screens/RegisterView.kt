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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

//Dark mode colour schemd
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkText = Color(0xFFFFFFFF)
private val DarkSecondaryText = Color(0xFFB0B0B0)
private val AccentBlue = Color(0xFF2962FF)
private val TextFieldBorder = Color(0xFF424242)

@Composable
fun RegisterView(navigate: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //title
                Text(
                    "poster",
                    style = MaterialTheme.typography.h4.copy(color = DarkText),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    //username field
                    Text("Username", style = MaterialTheme.typography.h6.copy(color = DarkText))
                    BasicTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = DarkText),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    //email field
                    Text("Email", style = MaterialTheme.typography.h6.copy(color = DarkText))
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = DarkText),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (email.isEmpty()) {
                                    Text(text = " you@example.com", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    //password field
                    Text("Password", style = MaterialTheme.typography.h6.copy(color = DarkText))
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, TextFieldBorder, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        textStyle = TextStyle(fontSize = 18.sp, color = DarkText),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (password.isEmpty()) {
                                    Text(text = " ●●●●●●●●●●", color = DarkSecondaryText)
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                //sign up button
                Button(
                    onClick = { navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(55.dp)
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AccentBlue,
                        contentColor = DarkText
                    )
                ) {
                    Text("Sign Up", fontSize = 18.sp)
                }

                //already have account link
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Already have an account? ", color = DarkText)
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
fun RegisterPagePreview() {
    RegisterView(navigate = {})
}
