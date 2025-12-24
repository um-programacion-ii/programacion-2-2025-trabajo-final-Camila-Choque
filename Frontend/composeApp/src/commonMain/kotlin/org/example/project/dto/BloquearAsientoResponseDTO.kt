package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class BloquearAsientoResponseDTO (
    val resultado: Boolean,
    val descripcion: String,
    val eventoId: Long,
    val asientos: List<AsientoCompletoDTO>
)
@Serializable
data class AsientoCompletoDTO (
    val fila: Int,
    val columna: Int,
    val estado: String,
)