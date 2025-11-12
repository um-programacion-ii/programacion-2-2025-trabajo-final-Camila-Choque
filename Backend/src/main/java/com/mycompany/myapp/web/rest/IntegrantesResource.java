package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.IntegrantesRepository;
import com.mycompany.myapp.service.IntegrantesService;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Integrantes}.
 */
@RestController
@RequestMapping("/api/integrantes")
public class IntegrantesResource {

    private static final Logger LOG = LoggerFactory.getLogger(IntegrantesResource.class);

    private static final String ENTITY_NAME = "integrantes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrantesService integrantesService;

    private final IntegrantesRepository integrantesRepository;

    public IntegrantesResource(IntegrantesService integrantesService, IntegrantesRepository integrantesRepository) {
        this.integrantesService = integrantesService;
        this.integrantesRepository = integrantesRepository;
    }

    /**
     * {@code POST  /integrantes} : Create a new integrantes.
     *
     * @param integrantesDTO the integrantesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrantesDTO, or with status {@code 400 (Bad Request)} if the integrantes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntegrantesDTO> createIntegrantes(@RequestBody IntegrantesDTO integrantesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Integrantes : {}", integrantesDTO);
        if (integrantesDTO.getId() != null) {
            throw new BadRequestAlertException("A new integrantes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        integrantesDTO = integrantesService.save(integrantesDTO);
        return ResponseEntity.created(new URI("/api/integrantes/" + integrantesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, integrantesDTO.getId().toString()))
            .body(integrantesDTO);
    }

    /**
     * {@code PUT  /integrantes/:id} : Updates an existing integrantes.
     *
     * @param id the id of the integrantesDTO to save.
     * @param integrantesDTO the integrantesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrantesDTO,
     * or with status {@code 400 (Bad Request)} if the integrantesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrantesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntegrantesDTO> updateIntegrantes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntegrantesDTO integrantesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Integrantes : {}, {}", id, integrantesDTO);
        if (integrantesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrantesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrantesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        integrantesDTO = integrantesService.update(integrantesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrantesDTO.getId().toString()))
            .body(integrantesDTO);
    }

    /**
     * {@code PATCH  /integrantes/:id} : Partial updates given fields of an existing integrantes, field will ignore if it is null
     *
     * @param id the id of the integrantesDTO to save.
     * @param integrantesDTO the integrantesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrantesDTO,
     * or with status {@code 400 (Bad Request)} if the integrantesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the integrantesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the integrantesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntegrantesDTO> partialUpdateIntegrantes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntegrantesDTO integrantesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Integrantes partially : {}, {}", id, integrantesDTO);
        if (integrantesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrantesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrantesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntegrantesDTO> result = integrantesService.partialUpdate(integrantesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrantesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /integrantes} : get all the integrantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrantes in body.
     */
    @GetMapping("")
    public List<IntegrantesDTO> getAllIntegrantes() {
        LOG.debug("REST request to get all Integrantes");
        return integrantesService.findAll();
    }

    /**
     * {@code GET  /integrantes/:id} : get the "id" integrantes.
     *
     * @param id the id of the integrantesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrantesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntegrantesDTO> getIntegrantes(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Integrantes : {}", id);
        Optional<IntegrantesDTO> integrantesDTO = integrantesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrantesDTO);
    }

    /**
     * {@code DELETE  /integrantes/:id} : delete the "id" integrantes.
     *
     * @param id the id of the integrantesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntegrantes(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Integrantes : {}", id);
        integrantesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
