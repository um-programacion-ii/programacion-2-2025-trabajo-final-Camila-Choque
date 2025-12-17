package org.example.project.Routeo

sealed class Interfaz {
    object Login : Interfaz()
    object ListarEventos : Interfaz()
}
