package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Sesion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {}
