package com.mycompany.myapp.infrastructure.persistence.repository;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaVentaRepository extends JpaRepository< Venta, Long> {
    List<Venta> findAllByUserId(Long userId);
}
