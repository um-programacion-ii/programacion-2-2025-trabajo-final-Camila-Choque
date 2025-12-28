package com.mycompany.myapp.infrastructure.persistence.repository;
import com.mycompany.myapp.domain.model.VentaModel;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaVentaRepository extends JpaRepository< Venta, Long> {
    List<Venta> findAllByUserId(Long userId);
}
