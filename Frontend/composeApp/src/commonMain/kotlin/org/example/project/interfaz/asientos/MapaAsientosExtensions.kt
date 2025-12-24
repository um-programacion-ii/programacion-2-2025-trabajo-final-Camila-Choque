package org.example.project.content.mapaAsientos
import org.example.project.dto.MapaAsientosDTO

fun MapaAsientosDTO.disponibles(): Int {
    return asientos.sumOf { fila ->
        fila.asientos.count { it.estado == "DISPONIBLE" }
    }
}