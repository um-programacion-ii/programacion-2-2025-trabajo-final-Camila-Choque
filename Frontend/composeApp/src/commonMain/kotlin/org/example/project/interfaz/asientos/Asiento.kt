package org.example.project.content.mapaAsientos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.dto.AsientoEstadoDTO

@Composable
fun Asiento(
    fila: Int,
    asiento: AsientoEstadoDTO,
    size: Dp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = when {
        isSelected -> Color(0xFF2196F3)
        asiento.estado == "DISPONIBLE" -> Color(0xFF4CAF50)
        asiento.estado == "BLOQUEADO" -> Color(0xFF9E9E9E)
        asiento.estado == "VENDIDO" -> Color(0xFFE53935)
        else -> Color.Gray
    }

    val enabled = asiento.estado == "DISPONIBLE"

    Box(
        modifier = Modifier
            .size(size)
            .background(color.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (size > 25.dp) {
            Text(
                text = "${asiento.columna}",
                fontSize = (size.value * 0.3f).sp,
                color = if (isSelected || asiento.estado != "DISPONIBLE") {
                    Color.White
                } else {
                    color.copy(alpha = 0.8f)
                },
                fontWeight = FontWeight.Bold
            )
        }
    }
}