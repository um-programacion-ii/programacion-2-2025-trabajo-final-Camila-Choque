package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.service.AsientosService;
import com.mycompany.myapp.service.dto.AsientosDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Asientos}.
 */
@RestController
@RequestMapping("/api/asientos")
public class AsientosResource {

    private static final Logger LOG = LoggerFactory.getLogger(AsientosResource.class);

    private static final String ENTITY_NAME = "asientos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsientosService asientosService;

    private final AsientosRepository asientosRepository;

    public AsientosResource(AsientosService asientosService, AsientosRepository asientosRepository) {
        this.asientosService = asientosService;
        this.asientosRepository = asientosRepository;
    }

    /**
     * {@code POST  /asientos} : Create a new asientos.
     *
     * @param asientosDTO the asientosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asientosDTO, or with status {@code 400 (Bad Request)} if the asientos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AsientosDTO> createAsientos(@RequestBody AsientosDTO asientosDTO) throws URISyntaxException {
        LOG.debug("REST request to save Asientos : {}", asientosDTO);
        if (asientosDTO.getId() != null) {
            throw new BadRequestAlertException("A new asientos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        asientosDTO = asientosService.save(asientosDTO);
        return ResponseEntity.created(new URI("/api/asientos/" + asientosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, asientosDTO.getId().toString()))
            .body(asientosDTO);
    }

    /**
     * {@code PUT  /asientos/:id} : Updates an existing asientos.
     *
     * @param id the id of the asientosDTO to save.
     * @param asientosDTO the asientosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asientosDTO,
     * or with status {@code 400 (Bad Request)} if the asientosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asientosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AsientosDTO> updateAsientos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AsientosDTO asientosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Asientos : {}, {}", id, asientosDTO);
        if (asientosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asientosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asientosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        asientosDTO = asientosService.update(asientosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asientosDTO.getId().toString()))
            .body(asientosDTO);
    }

    /**
     * {@code PATCH  /asientos/:id} : Partial updates given fields of an existing asientos, field will ignore if it is null
     *
     * @param id the id of the asientosDTO to save.
     * @param asientosDTO the asientosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asientosDTO,
     * or with status {@code 400 (Bad Request)} if the asientosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the asientosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the asientosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsientosDTO> partialUpdateAsientos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AsientosDTO asientosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Asientos partially : {}, {}", id, asientosDTO);
        if (asientosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asientosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asientosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AsientosDTO> result = asientosService.partialUpdate(asientosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asientosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asientos} : get all the asientos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asientos in body.
     */
    @GetMapping("")
    public List<AsientosDTO> getAllAsientos() {
        LOG.debug("REST request to get all Asientos");
        return asientosService.findAll();
    }

    /**
     * {@code GET  /asientos/:id} : get the "id" asientos.
     *
     * @param id the id of the asientosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asientosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AsientosDTO> getAsientos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Asientos : {}", id);
        Optional<AsientosDTO> asientosDTO = asientosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asientosDTO);
    }

    /**
     * {@code DELETE  /asientos/:id} : delete the "id" asientos.
     *
     * @param id the id of the asientosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsientos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Asientos : {}", id);
        asientosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
