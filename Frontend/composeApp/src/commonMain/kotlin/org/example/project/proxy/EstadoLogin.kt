package org.example.project.proxy

sealed class EstadoLogin {
    object Estatico : EstadoLogin()
    object Cargando : EstadoLogin()
    data class Exitoso(val token: String) : EstadoLogin()
    data class Error(val message: String) : EstadoLogin()
}
