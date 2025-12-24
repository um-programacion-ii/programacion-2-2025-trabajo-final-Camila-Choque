package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class AsientoDTO(
    val fila: Int,
    val columna: Int
)