package org.example.project.proxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.dto.CompraDTO

class ModeloCompra : ViewModel() {

    private val _compras = MutableStateFlow<List<CompraDTO>>(emptyList())
    val compras: StateFlow<List<CompraDTO>> = _compras

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadCompras() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = ApiClient.getMisCompras()

            result
                .onSuccess {
                    _compras.value = it
                }
                .onFailure {
                    _error.value = it.message ?: "Error cargando compras"
                }

            _loading.value = false
        }
    }
}
