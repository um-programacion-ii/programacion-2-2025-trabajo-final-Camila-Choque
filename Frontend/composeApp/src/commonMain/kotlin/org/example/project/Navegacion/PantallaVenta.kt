package org.example.project.Navegacion
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.content.venta.VentaInterfaz
import org.example.project.dto.AsientoDTO

data class PantallaVenta(
    val eventoId: Long,
    val seats: List<AsientoDTO>,
    val precioUnitario: Double
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        VentaInterfaz(
            eventoId = eventoId,
            seats = seats,
            onBack = { navigator.pop() },
            onSuccess = { navigator.pop() },
            precioUnitario = precioUnitario
        )
    }
}