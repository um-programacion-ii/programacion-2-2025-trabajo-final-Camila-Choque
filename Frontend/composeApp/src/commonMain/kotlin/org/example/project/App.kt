package org.example.project

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.Navegacion.PantallaLogin

@Composable
fun App() {
    Navigator(screen = PantallaLogin())
}