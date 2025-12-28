package com.mycompany.myapp.application.service;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.domain.port.in.RegistroComprasUseCase;
import com.mycompany.myapp.domain.port.out.VentaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class ObtenerVentasPorUsuarioUseCase implements RegistroComprasUseCase {

    private final VentaRepository ventaRepository;

    public ObtenerVentasPorUsuarioUseCase(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<VentaModel> obtenerVentasPorUsuario(Long userId) {
        return ventaRepository.findByUserId(userId);
    }
}
