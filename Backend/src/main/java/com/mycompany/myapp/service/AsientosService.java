package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AsientosDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Asientos}.
 */
public interface AsientosService {
    /**
     * Save a asientos.
     *
     * @param asientosDTO the entity to save.
     * @return the persisted entity.
     */
    AsientosDTO save(AsientosDTO asientosDTO);

    /**
     * Updates a asientos.
     *
     * @param asientosDTO the entity to update.
     * @return the persisted entity.
     */
    AsientosDTO update(AsientosDTO asientosDTO);

    /**
     * Partially updates a asientos.
     *
     * @param asientosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AsientosDTO> partialUpdate(AsientosDTO asientosDTO);

    /**
     * Get all the asientos.
     *
     * @return the list of entities.
     */
    List<AsientosDTO> findAll();

    /**
     * Get the "id" asientos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AsientosDTO> findOne(Long id);

    /**
     * Delete the "id" asientos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
