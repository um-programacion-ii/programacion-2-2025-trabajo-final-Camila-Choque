package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SesionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Sesion}.
 */
public interface SesionService {
    /**
     * Save a sesion.
     *
     * @param sesionDTO the entity to save.
     * @return the persisted entity.
     */
    SesionDTO save(SesionDTO sesionDTO);

    /**
     * Updates a sesion.
     *
     * @param sesionDTO the entity to update.
     * @return the persisted entity.
     */
    SesionDTO update(SesionDTO sesionDTO);

    /**
     * Partially updates a sesion.
     *
     * @param sesionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SesionDTO> partialUpdate(SesionDTO sesionDTO);

    /**
     * Get all the sesions.
     *
     * @return the list of entities.
     */
    List<SesionDTO> findAll();

    /**
     * Get the "id" sesion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SesionDTO> findOne(Long id);

    /**
     * Delete the "id" sesion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
