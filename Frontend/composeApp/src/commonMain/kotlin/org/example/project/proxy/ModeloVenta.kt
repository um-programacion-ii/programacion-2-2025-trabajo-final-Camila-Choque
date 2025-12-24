package org.example.project.proxy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.AsientoDTO
import org.example.project.dto.AsientosComprasDTO
import org.example.project.dto.VentaAsientoRequestDTO
import org.example.project.estados.EstadoVenta
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ModeloVenta {

    private val _uiState = MutableStateFlow<EstadoVenta>(EstadoVenta.Estatico)
    val uiState: StateFlow<EstadoVenta> = _uiState.asStateFlow()
    private var precioUnitario: Double = 0.0
    private var _asientosSeleccionados: List<AsientoDTO> = emptyList()
    val asientosSeleccionados: List<AsientoDTO>
        get() = _asientosSeleccionados
    suspend fun iniciarVenta(
        eventoId: Long,
        seats: List<AsientoDTO>,
        precioUnitario: Double
    ) {
        this.precioUnitario = precioUnitario
        this._asientosSeleccionados = seats
        bloquearAsientos(eventoId, seats)
    }
    suspend fun bloquearAsientos(
        eventoId: Long,
        asientos: List<AsientoDTO>
    ) {
        _uiState.value = EstadoVenta.Bloqueando

        val result = ApiClient.bloquearAsientos(eventoId, asientos)

        result.fold(
            onSuccess = {
                _uiState.value = EstadoVenta.Bloqueado
            },
            onFailure = { error ->
                _uiState.value = EstadoVenta.Error(
                    error.message ?: "Error al bloquear asientos"
                )
            }
        )
    }

    @OptIn(ExperimentalTime::class)
    suspend fun venderAsientos(
        eventoId: Long,
        precioTotal: Double,
        persona: String
    ) {
        _uiState.value = EstadoVenta.Vendiendo

        val request = VentaAsientoRequestDTO(
            eventoId = eventoId,
            fecha = Clock.System.now().toString(),
            precioVenta = precioTotal,
            asientos = _asientosSeleccionados.map {
                AsientosComprasDTO(
                    fila = it.fila,
                    columna = it.columna,
                    persona = persona,
                )
            }
        )

        val result = ApiClient.venderAsientos(eventoId, request)

        result.fold(
            onSuccess = {
                _uiState.value = EstadoVenta.Exitoso
            },
            onFailure = { error ->
                _uiState.value = EstadoVenta.Error(
                    error.message ?: "Error al vender asientos"
                )
            }
        )
    }

    fun total() =
        _asientosSeleccionados.size * precioUnitario
}