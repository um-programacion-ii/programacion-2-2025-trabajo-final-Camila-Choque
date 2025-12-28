package org.example.project.estados

sealed class EstadoRegistro {
    object Estatico : EstadoRegistro()
    object Cargando : EstadoRegistro()
    data class Exitoso(val mensaje: String) : EstadoRegistro()
    data class Error(val message: String) : EstadoRegistro()
}
