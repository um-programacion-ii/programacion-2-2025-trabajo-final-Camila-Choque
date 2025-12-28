package org.example.project.interfaz
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.dto.EventoDTO
import org.example.project.proxy.ModeloCompra
import org.example.project.proxy.ModeloEvento

@Composable
fun EventoInterfaz(
    onEventClick: (EventoDTO) -> Unit
) {
    val eventoViewModel = remember { ModeloEvento() }
    val comprasViewModel = remember { ModeloCompra() }

    val scope = rememberCoroutineScope()
    var mostrarCompras by remember { mutableStateOf(false) }

    val events by eventoViewModel.events.collectAsState()
    val loading by eventoViewModel.loading.collectAsState()
    val error by eventoViewModel.error.collectAsState()

    val compras by comprasViewModel.compras.collectAsState()
    val loadingCompras by comprasViewModel.loading.collectAsState()
    val errorCompras by comprasViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        eventoViewModel.loadEvents()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        //  BOTÓN SUPERIOR
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                mostrarCompras = !mostrarCompras
                if (mostrarCompras) {
                    comprasViewModel.loadCompras()
                }
            }
        ) {
            Text(if (mostrarCompras) "Ocultar mis compras" else "Ver mis compras")
        }

        // LISTA  COMPRAS
        if (mostrarCompras) {
            when {
                loadingCompras -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                errorCompras != null -> {
                    Text(
                        text = errorCompras ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                compras.isEmpty() -> {
                    Text(
                        text = "No realizaste compras aún",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 250.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = compras,
                            key = { it.id ?: it.hashCode() }
                        ) { compra ->
                            CompraItem(compra)
                        }

                    }
                }
            }
        }

        // LISTA DE EVENTOS
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    eventoViewModel.loadEvents()
                                }
                            }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                events.isEmpty() -> {
                    Text(
                        text = "No hay eventos disponibles",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = events,
                            key = { event -> event.id }
                        ) { event ->
                            EventoItem(
                                event = event,
                                onClick = { onEventClick(event) }
                            )
                        }
                    }
                }
            }
        }
    }
}
