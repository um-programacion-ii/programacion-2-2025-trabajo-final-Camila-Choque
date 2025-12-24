package org.example.project.interfaz
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.example.project.Navegacion.PantallaEvento
import org.example.project.content.mapaAsientos.SeatMapScreen
import org.example.project.dto.MapaAsientosDTO
import org.example.project.proxy.ApiClient


@Composable
fun AsientosInterfaz(
    eventId: Long,
    precioUnitario: Double,
    onBack: () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow

    var mapaAsientos by remember { mutableStateOf<MapaAsientosDTO?>(null) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(eventId) {
        loading = true
        val result = ApiClient.getAsientosEvento(eventId)
        result.fold(
            onSuccess = { mapaAsientos = it },
            onFailure = { error -> println("Error: ${error.message}") }
        )
        loading = false
    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        mapaAsientos != null -> {
            SeatMapScreen(
                eventoId = eventId,
                mapaAsientos = mapaAsientos!!,
                onSeatsSelected = { seats ->
                    navigator.push(
                       /*
                        SeatDetailsScreen(
                            eventoId = eventId,
                            seats = seats,
                            precioUnitario = precioUnitario
                        )
                        */
                        PantallaEvento()
                    )
                },
                onBack = onBack
            )
        }
    }
}