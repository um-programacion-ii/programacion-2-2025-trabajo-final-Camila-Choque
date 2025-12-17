package org.example.project.proxy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.project.dto.EventoDTO

class ModeloEvento {

    var events by mutableStateOf<List<EventoDTO>>(emptyList())
        private set

    var loading by mutableStateOf(true)
        private set

    suspend fun loadEvents() {
        events = ApiClient.obtenerEventos()
        loading = false
    }
}