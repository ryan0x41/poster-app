package org.group_h.poster

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginPage(navigate: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // define text of h2, using text "Login"
            Text("Login", style = MaterialTheme.typography.h2)

            // a spacer between the first input and h2
            Spacer(modifier = Modifier.height(32.dp))

            // some label username
            Text("Username")
            // text field for username with a decorationBox to create a placeholder
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (username.isEmpty()) {
                            Text(
                                text = "exampleUsername123",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )

            // some label for password input
            Text("Password")
            // password field with placeholder
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (password.isEmpty()) {
                            Text(
                                text = "●●●●●●●●●●●",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )

            // spacer between password input and login button
            Spacer(modifier = Modifier.height(32.dp))

            // login button which will navigate to home page
            Button(
                onClick = { navigate("home") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Login")
            }

            // login button which will navigate to createAccount page
            Button(
                onClick = { navigate("createAccount") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Create Account")
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Welcome to Home Screen!")
}

@Composable
fun CreateAccountScreen() {
    Text("Create Account Screen")
}

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage(navigate = {})
}
