package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.IntegrantesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Integrantes}.
 */
public interface IntegrantesService {
    /**
     * Save a integrantes.
     *
     * @param integrantesDTO the entity to save.
     * @return the persisted entity.
     */
    IntegrantesDTO save(IntegrantesDTO integrantesDTO);

    /**
     * Updates a integrantes.
     *
     * @param integrantesDTO the entity to update.
     * @return the persisted entity.
     */
    IntegrantesDTO update(IntegrantesDTO integrantesDTO);

    /**
     * Partially updates a integrantes.
     *
     * @param integrantesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IntegrantesDTO> partialUpdate(IntegrantesDTO integrantesDTO);

    /**
     * Get all the integrantes.
     *
     * @return the list of entities.
     */
    List<IntegrantesDTO> findAll();

    /**
     * Get the "id" integrantes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrantesDTO> findOne(Long id);

    /**
     * Delete the "id" integrantes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
