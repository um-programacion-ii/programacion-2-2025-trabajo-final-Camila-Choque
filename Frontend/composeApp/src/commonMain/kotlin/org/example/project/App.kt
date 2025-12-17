package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.compose_multiplatform
import org.example.project.Routeo.Interfaz
import org.example.project.interfaz.LoginScreen

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Interfaz>(Interfaz.Login) }

    when (val screen = currentScreen) {

        Interfaz.Login -> {
            LoginScreen(
                cambioDePantalla = {
                    currentScreen = Interfaz.ListarEventos
                }
            )
        }

        Interfaz.ListarEventos -> TODO()
    }
}