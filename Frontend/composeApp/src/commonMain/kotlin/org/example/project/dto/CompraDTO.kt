package org.example.project.dto

data class CompraDTO (
    val id: Long?= null ,
    val ventaIdCatedra: Long?= null,
    val fechaVenta: String,
    val precioVenta: Double,
    val resultado: Boolean,
    val descripcion: String,
    val cantidadAsientos: Int,
    val estado: String

)