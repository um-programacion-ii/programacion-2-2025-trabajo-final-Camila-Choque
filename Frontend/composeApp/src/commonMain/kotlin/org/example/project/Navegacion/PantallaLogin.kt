package org.example.project.Navegacion
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.LoginDTO
import org.example.project.estados.EstadoLogin
import org.example.project.proxy.ApiClient

class PantallaLogin {
    private val _uiState = MutableStateFlow<EstadoLogin>(EstadoLogin.Estatico)
    val uiState: StateFlow<EstadoLogin> = _uiState.asStateFlow()

    suspend fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = EstadoLogin.Error("Usuario y contraseña son requeridos")
            return
        }

        _uiState.value = EstadoLogin.Cargando

        val result = ApiClient.login(LoginDTO(username, password))

        result.fold(
            onSuccess = { token ->
                _uiState.value = EstadoLogin.Exitoso("Login exitoso")
            },
            onFailure = { error ->
                val message = when (error) {
                    is HttpRequestTimeoutException -> "Tiempo de espera agotado"
                    is ConnectTimeoutException -> "No se pudo conectar al servidor"
                    is SocketTimeoutException -> "Conexión perdida"
                    else -> error.message ?: "Error en el login"
                }
                _uiState.value = EstadoLogin.Error(message)
            }
        )
    }

    fun resetState() {
        _uiState.value = EstadoLogin.Estatico
    }
}