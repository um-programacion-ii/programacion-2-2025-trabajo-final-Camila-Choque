package com.mycompany.myapp.application.service;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.domain.port.in.RegistroComprasUseCase;
import com.mycompany.myapp.domain.port.out.VentaRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class ObtenerVentasPorUsuarioUseCase implements RegistroComprasUseCase {

    private final UserRepository userRepository;
    private final VentaRepository ventaRepository;

    public ObtenerVentasPorUsuarioUseCase(UserRepository userRepository, VentaRepository ventaRepository) {
        this.userRepository = userRepository;
        this.ventaRepository = ventaRepository;
    }

    public List<VentaModel> obtenerVentasPorUsuario() {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ventaRepository.findByUserId(user.getId());
    }
}
