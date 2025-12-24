package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class BloquearAsientosRequestDTO(
    val eventoId: Long,
    val asientos: List<AsientoDTO>
)
