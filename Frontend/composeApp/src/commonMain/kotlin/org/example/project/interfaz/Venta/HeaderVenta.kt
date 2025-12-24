package org.example.project.content.venta
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HeaderVenta(onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
            Text("←", fontSize = 22.sp)
        }
        Text(
            "Datos de la compra",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}