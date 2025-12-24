package org.example.project.Navegacion
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.dto.EventoDTO
import org.example.project.interfaz.AsientosInterfaz

data class PantallaAsientos(val event: EventoDTO) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        AsientosInterfaz(
            eventId = event.id,
            precioUnitario = event.precioEntrada,
            onBack = { navigator.pop() }
        )
    }
}