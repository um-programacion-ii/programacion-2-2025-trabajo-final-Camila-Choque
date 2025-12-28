package org.example.project.proxy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.dto.RegistrarDTO
import org.example.project.estados.EstadoRegistro

class ModeloRegistro {

    private val _uiState = MutableStateFlow<EstadoRegistro>(EstadoRegistro.Estatico)
    val uiState: StateFlow<EstadoRegistro> = _uiState.asStateFlow()

    suspend fun registrar(login: String, password: String, email: String, firstName: String, lastName: String) {
        if (login.isBlank() || password.isBlank()) {
            _uiState.value = EstadoRegistro.Error("Usuario y contraseña son requeridos")
            return
        }

        _uiState.value = EstadoRegistro.Cargando

        val result = ApiClient.registrar(
            RegistrarDTO(
                login,
                password,
                email,
                firstName,
                lastName,
                "es"
            )
        )

        result.fold(
            onSuccess = {
                _uiState.value = EstadoRegistro.Exitoso("Registro exitoso")
            },
            onFailure = { error ->
                _uiState.value = EstadoRegistro.Error(
                    error.message ?: "Error en el registro"
                )
            }
        )
    }

    fun resetState() {
        _uiState.value = EstadoRegistro.Estatico
    }
}