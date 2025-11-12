package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Integrantes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Integrantes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntegrantesRepository extends JpaRepository<Integrantes, Long> {}
