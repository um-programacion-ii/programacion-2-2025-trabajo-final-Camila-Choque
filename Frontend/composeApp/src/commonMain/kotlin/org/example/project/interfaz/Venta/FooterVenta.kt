package org.example.project.content.venta
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FooterVenta(
    total: Double,
    loading: Boolean,
    error: String?,
    onConfirm: () -> Unit
) {
    Text(
        "Total: $$total",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(12.dp))

    error?.let {
        Text(it, color = Color.Red)
        Spacer(Modifier.height(8.dp))
    }

    Button(
        onClick = onConfirm,
        modifier = Modifier.fillMaxWidth(),
        enabled = !loading
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                "Confirmar compra",
                fontWeight = FontWeight.Bold
            )
        }
    }
}