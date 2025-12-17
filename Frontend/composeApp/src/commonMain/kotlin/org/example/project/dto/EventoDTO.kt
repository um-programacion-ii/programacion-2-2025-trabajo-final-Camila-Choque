package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class EventoDTO(
    val id: Long,
    val titulo: String,
    val fecha: String,
    val descripcion: String,
    val precioEntrada: Double,
    val eventoTipo: EventoTipoDTO,
)

@Serializable
data class EventoTipoDTO(
    val nombre: String,
    val descripcion: String
)