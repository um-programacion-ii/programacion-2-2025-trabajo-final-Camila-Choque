package org.example.project.dto

import kotlinx.serialization.Serializable

@Serializable
data class MapaAsientosDTO(
    val eventoId: Long,
    val filas: Int,
    val columnas: Int,
    val asientos: List<FilaAsientosDTO>
)