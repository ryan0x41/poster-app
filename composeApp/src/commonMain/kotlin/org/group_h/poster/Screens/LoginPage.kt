package org.group_h.poster.Screens

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //title
                Text("poster", style = MaterialTheme.typography.h4, modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(32.dp))

                // username/email text field
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text("Username/Email", style = MaterialTheme.typography.h6)
                    BasicTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (username.isEmpty()) {
                                    Text(text = " exampleUsername123", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // password field
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Text("Password", style = MaterialTheme.typography.h6)
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (password.isEmpty()) {
                                    Text(text = "●●●●●●●●●●●", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                }


                Spacer(modifier = Modifier.height(0.dp))
                LoginCheckbox()

                Spacer(modifier = Modifier.height(24.dp))

                // login button
                Button(
                    onClick = { navigate("home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Sign In", style = MaterialTheme.typography.h6)
                }

                // dont have account with clickable text that brings user to register page
                Spacer(modifier = Modifier.height(0.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Don't have an account yet? ")
                    Text(
                        text = "Sign Up",
                        color = Color.Blue,
                        modifier = Modifier.clickable { navigate("createAccount") }
                    )
                }
            }
        }
    }
}

// login checkbox to remember account
@Composable
fun LoginCheckbox(){
    var checked by remember { mutableStateOf(true) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text("Remember me")
        }
        Text(
            "Forgot Password?",
            color = Color.Blue,
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
