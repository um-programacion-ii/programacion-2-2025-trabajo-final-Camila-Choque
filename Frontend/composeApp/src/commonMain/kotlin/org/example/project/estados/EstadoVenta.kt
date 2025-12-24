package org.example.project.estados

sealed class EstadoVenta {
    object Estatico : EstadoVenta()
    object Bloqueando : EstadoVenta()
    object Bloqueado : EstadoVenta()
    object Vendiendo : EstadoVenta()
    object Exitoso : EstadoVenta()
    data class Error(val message: String) : EstadoVenta()
}