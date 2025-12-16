package org.example.project.proxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.dto.LoginDTO

class ModeloLogin() : ViewModel(){
    private val _uiState = MutableStateFlow<EstadoLogin>(EstadoLogin.Estatico)
    val uiState: StateFlow<EstadoLogin> = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = EstadoLogin.Cargando
            try {
                val token = ApiClient.login(
                    LoginDTO(username, password)
                )
                _uiState.value = EstadoLogin.Exitoso(token)
            } catch (e: Exception) {
                _uiState.value =
                    EstadoLogin.Error("Usuario o contraseña incorrectos")
            }
        }
    }

}
