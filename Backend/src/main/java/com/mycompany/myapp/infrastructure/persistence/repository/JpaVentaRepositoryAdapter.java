package com.mycompany.myapp.infrastructure.persistence.repository;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.domain.port.out.VentaRepository;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import com.mycompany.myapp.infrastructure.persistence.mapper.VentaMapper;
import com.mycompany.myapp.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JpaVentaRepositoryAdapter implements VentaRepository {

    private final JpaVentaRepository jpaVentaRepository;
    private final VentaMapper ventaMapper;
    private final UserRepository userRepository;

    public JpaVentaRepositoryAdapter(
        JpaVentaRepository jpaVentaRepository,
        VentaMapper ventaMapper,
        UserRepository userRepository
    ) {
        this.jpaVentaRepository = jpaVentaRepository;
        this.ventaMapper = ventaMapper;
        this.userRepository = userRepository;
    }

    @Override
    public VentaModel save(VentaModel ventaModel) {

        User user = userRepository.findById(ventaModel.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Venta ventaEntity = ventaMapper.toEntity(ventaModel, user);

        Venta savedEntity = jpaVentaRepository.save(ventaEntity);

        return ventaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<VentaModel> findById(Long id) {
        return jpaVentaRepository.findById(id)
            .map(ventaMapper::toDomain);
    }

    @Override
    public List<VentaModel> findAll() {
        return jpaVentaRepository.findAll()
            .stream()
            .map(ventaMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<VentaModel> update(Long id, VentaModel ventaModel) {
        return jpaVentaRepository.findById(id)
            .map(existing -> {

                User user = userRepository.findById(ventaModel.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

                Venta updated = ventaMapper.toEntity(ventaModel, user);
                updated.setId(existing.getId()); // MUY IMPORTANTE

                Venta saved = jpaVentaRepository.save(updated);

                return ventaMapper.toDomain(saved);
            });
    }

    @Override
    public boolean delete(Long id) {
        if (!jpaVentaRepository.existsById(id)) {
            return false;
        }
        jpaVentaRepository.deleteById(id);
        return true;
    }

    @Override
    public List<VentaModel> findByUserId(Long userId) {
        return jpaVentaRepository.findAllByUserId(userId)
            .stream()
            .map(ventaMapper::toDomain)
            .toList();
    }
}
