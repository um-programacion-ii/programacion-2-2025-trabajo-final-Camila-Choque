package org.example.project.content.mapaAsientos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.dto.AsientoDTO
import org.example.project.dto.FilaAsientosDTO

@Composable
fun FilaAsientos(
    filaData: FilaAsientosDTO,
    seatSize: Dp,
    selectedSeats: List<AsientoDTO>,
    onSeatClick: (Int, Int, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Número de fila
        Text(
            text = "${filaData.fila}",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .width(30.dp)
                .padding(end = 8.dp),
            textAlign = TextAlign.Center
        )

        // Asientos
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(filaData.asientos) { asiento ->
                Asiento(
                    fila = filaData.fila,
                    asiento = asiento,
                    size = seatSize,
                    isSelected = selectedSeats.contains(
                        AsientoDTO(filaData.fila, asiento.columna)
                    ),
                    onClick = { onSeatClick(filaData.fila, asiento.columna, asiento.estado) }
                )
            }
        }
    }
}