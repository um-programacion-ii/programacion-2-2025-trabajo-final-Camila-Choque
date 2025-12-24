package org.example.project.content.venta
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.dto.AsientoDTO
import org.example.project.estados.EstadoVenta
import org.example.project.proxy.ModeloVenta


@Composable
fun VentaInterfaz(
    eventoId: Long,
    seats: List<AsientoDTO>,
    precioUnitario: Double,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val viewModel = remember { ModeloVenta() }
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is EstadoVenta.Exitoso) {
            onSuccess()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.iniciarVenta(
            eventoId = eventoId,
            seats = seats,
            precioUnitario = precioUnitario,
        )
    }

    when (uiState) {
        EstadoVenta.Bloqueando -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(8.dp))
                Text("Bloqueando asientos...")
            }
        }

        EstadoVenta.Vendiendo -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is EstadoVenta.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (uiState as EstadoVenta.Error).message,
                    color = Color.Red
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                HeaderVenta(onBack)

                Spacer(Modifier.height(16.dp))

                ListaAsientos(viewModel.asientosSeleccionados)

                Spacer(Modifier.height(16.dp))

                Text(
                    "Total: $${viewModel.total()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.venderAsientos(
                                eventoId = eventoId,
                                precioTotal = viewModel.total(),
                                persona = "admin",
                            )
                        }
                    },
                    enabled = uiState is EstadoVenta.Bloqueado,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar compra")
                }
            }
        }
    }
}
