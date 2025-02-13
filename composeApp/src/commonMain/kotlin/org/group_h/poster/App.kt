package org.group_h.poster

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poster.composeapp.generated.resources.Res
import poster.composeapp.generated.resources.compose_multiplatform
import org.group_h.poster.LoginPage as LoginPage

@Composable
fun App() {
    MaterialTheme {
        // reference our login page, load this first
        LoginPage(navigate = {})
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}
