package org.group_h.poster

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginPage(navigate: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // define text of h2, using text "Login"
            Text("Sign into your account", style = MaterialTheme.typography.h4)

            // a spacer between the first input and h2
            Spacer(modifier = Modifier.height(32.dp))

            // some label username
            Text("Your email",style = MaterialTheme.typography.h6)
            // text field for username with a decorationBox to create a placeholder
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth().height(56.dp).border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)).padding(horizontal = 12.dp),
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    )  {
                        if (username.isEmpty()) {
                            Text(
                                text = " exampleUsername123",
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Text("")

            // some label for password input
            Text("Your Password",style = MaterialTheme.typography.h6)
            // password field with placeholder
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().height(56.dp).border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)).padding(horizontal = 12.dp),
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
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

            LoginCheckbox()

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

//checkbox
@Composable
fun LoginCheckbox(){
    var checked by remember { mutableStateOf(true) }

    Row( // Use Column to structure elements properly
        verticalAlignment = Alignment.CenterVertically, // Aligns checkbox & text in the same row
        horizontalArrangement = Arrangement.SpaceBetween, // Distributes elements properly
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Remember me")
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }

        Text(
            "Forgot Password?",
            color = Color.Blue,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.clickable{

            }
        )
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
