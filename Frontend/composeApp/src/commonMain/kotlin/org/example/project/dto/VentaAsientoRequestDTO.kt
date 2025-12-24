package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class VentaAsientoRequestDTO (
    val eventoId: Long,
    val fecha: String,
    val precioVenta: Double,
    val asientos: List<AsientosComprasDTO>
)

@Serializable
data class AsientosComprasDTO (
    val fila: Int,
    val columna: Int,
    val persona: String,
)