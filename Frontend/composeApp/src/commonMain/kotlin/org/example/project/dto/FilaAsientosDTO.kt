package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class FilaAsientosDTO(
    val fila: Int,
    val asientos: List<AsientoEstadoDTO>
)