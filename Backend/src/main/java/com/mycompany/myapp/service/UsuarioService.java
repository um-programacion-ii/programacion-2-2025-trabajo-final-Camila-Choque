package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UsuarioDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Usuario}.
 */
public interface UsuarioService {
    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save.
     * @return the persisted entity.
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     * Updates a usuario.
     *
     * @param usuarioDTO the entity to update.
     * @return the persisted entity.
     */
    UsuarioDTO update(UsuarioDTO usuarioDTO);

    /**
     * Partially updates a usuario.
     *
     * @param usuarioDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsuarioDTO> partialUpdate(UsuarioDTO usuarioDTO);

    /**
     * Get all the usuarios.
     *
     * @return the list of entities.
     */
    List<UsuarioDTO> findAll();

    /**
     * Get the "id" usuario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsuarioDTO> findOne(Long id);

    /**
     * Delete the "id" usuario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
