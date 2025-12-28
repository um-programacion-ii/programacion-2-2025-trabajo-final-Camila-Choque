package com.mycompany.myapp.domain.port.in;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import java.util.List;

public interface RegistroComprasUseCase {
    List<VentaModel> obtenerVentasPorUsuario();
}
