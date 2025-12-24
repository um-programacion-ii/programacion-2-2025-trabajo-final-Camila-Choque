package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class AsientoEstadoDTO(
    val columna: Int,
    val estado: String
)