package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Asientos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Asientos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsientosRepository extends JpaRepository<Asientos, Long> {
    List<Asientos> findByEventoId(Long eventoId);
}
