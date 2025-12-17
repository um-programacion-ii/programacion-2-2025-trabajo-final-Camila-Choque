package org.example.project.interfaz
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.dto.EventoDTO
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import org.example.project.proxy.ModeloEvento

@Composable
fun EventoInterfaz (
    onEventClick: (Long) -> Unit
) {
    val viewModel = remember { ModeloEvento() }

    LaunchedEffect(Unit) {
        viewModel.loadEvents()
    }

    if (viewModel.loading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(viewModel.events) { event ->
                FilaEvento(event) {
                    onEventClick(event.id)
                }
            }
        }
    }
}

@Composable
fun FilaEvento(event: EventoDTO, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.titulo, style = MaterialTheme.typography.titleMedium)
            Text(event.fecha, style = MaterialTheme.typography.bodySmall)
        }
    }
}