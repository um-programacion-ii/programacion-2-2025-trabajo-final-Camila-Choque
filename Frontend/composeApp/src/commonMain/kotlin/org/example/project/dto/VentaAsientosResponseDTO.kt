package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class VentaAsientosResponseDTO (
    val eventoId: Long,
    val ventaId: Long ?= null,
    val fechaVenta: String,
    val asientos: List<AsientosVentasDTO>,
    val resultado: Boolean,
    val descripcion: String,
    val precioVenta: Double
)
@Serializable
data class AsientosVentasDTO (
    val fila: Int,
    val columna: Int,
    val persona: String,
    val estado: String ?= null
)


