package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.AsientosAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.repository.AsientosRepository;
import com.mycompany.myapp.service.dto.AsientosDTO;
import com.mycompany.myapp.service.mapper.AsientosMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AsientosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsientosResourceIT {

    private static final Integer DEFAULT_FILA = 1;
    private static final Integer UPDATED_FILA = 2;

    private static final Integer DEFAULT_COLUMNA = 1;
    private static final Integer UPDATED_COLUMNA = 2;

    private static final String DEFAULT_PERSONA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AsientosRepository asientosRepository;

    @Autowired
    private AsientosMapper asientosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsientosMockMvc;

    private Asientos asientos;

    private Asientos insertedAsientos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asientos createEntity() {
        return new Asientos().fila(DEFAULT_FILA).columna(DEFAULT_COLUMNA).persona(DEFAULT_PERSONA).estado(DEFAULT_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asientos createUpdatedEntity() {
        return new Asientos().fila(UPDATED_FILA).columna(UPDATED_COLUMNA).persona(UPDATED_PERSONA).estado(UPDATED_ESTADO);
    }

    @BeforeEach
    void initTest() {
        asientos = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAsientos != null) {
            asientosRepository.delete(insertedAsientos);
            insertedAsientos = null;
        }
    }

    @Test
    @Transactional
    void createAsientos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);
        var returnedAsientosDTO = om.readValue(
            restAsientosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asientosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AsientosDTO.class
        );

        // Validate the Asientos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAsientos = asientosMapper.toEntity(returnedAsientosDTO);
        assertAsientosUpdatableFieldsEquals(returnedAsientos, getPersistedAsientos(returnedAsientos));

        insertedAsientos = returnedAsientos;
    }

    @Test
    @Transactional
    void createAsientosWithExistingId() throws Exception {
        // Create the Asientos with an existing ID
        asientos.setId(1L);
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsientosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asientosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAsientos() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        // Get all the asientosList
        restAsientosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asientos.getId().intValue())))
            .andExpect(jsonPath("$.[*].fila").value(hasItem(DEFAULT_FILA)))
            .andExpect(jsonPath("$.[*].columna").value(hasItem(DEFAULT_COLUMNA)))
            .andExpect(jsonPath("$.[*].persona").value(hasItem(DEFAULT_PERSONA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getAsientos() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        // Get the asientos
        restAsientosMockMvc
            .perform(get(ENTITY_API_URL_ID, asientos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asientos.getId().intValue()))
            .andExpect(jsonPath("$.fila").value(DEFAULT_FILA))
            .andExpect(jsonPath("$.columna").value(DEFAULT_COLUMNA))
            .andExpect(jsonPath("$.persona").value(DEFAULT_PERSONA))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingAsientos() throws Exception {
        // Get the asientos
        restAsientosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsientos() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asientos
        Asientos updatedAsientos = asientosRepository.findById(asientos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsientos are not directly saved in db
        em.detach(updatedAsientos);
        updatedAsientos.fila(UPDATED_FILA).columna(UPDATED_COLUMNA).persona(UPDATED_PERSONA).estado(UPDATED_ESTADO);
        AsientosDTO asientosDTO = asientosMapper.toDto(updatedAsientos);

        restAsientosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asientosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asientosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAsientosToMatchAllProperties(updatedAsientos);
    }

    @Test
    @Transactional
    void putNonExistingAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asientosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asientosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asientosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asientosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsientosWithPatch() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asientos using partial update
        Asientos partialUpdatedAsientos = new Asientos();
        partialUpdatedAsientos.setId(asientos.getId());

        partialUpdatedAsientos.fila(UPDATED_FILA).persona(UPDATED_PERSONA).estado(UPDATED_ESTADO);

        restAsientosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsientos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsientos))
            )
            .andExpect(status().isOk());

        // Validate the Asientos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsientosUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAsientos, asientos), getPersistedAsientos(asientos));
    }

    @Test
    @Transactional
    void fullUpdateAsientosWithPatch() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asientos using partial update
        Asientos partialUpdatedAsientos = new Asientos();
        partialUpdatedAsientos.setId(asientos.getId());

        partialUpdatedAsientos.fila(UPDATED_FILA).columna(UPDATED_COLUMNA).persona(UPDATED_PERSONA).estado(UPDATED_ESTADO);

        restAsientosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsientos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsientos))
            )
            .andExpect(status().isOk());

        // Validate the Asientos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsientosUpdatableFieldsEquals(partialUpdatedAsientos, getPersistedAsientos(partialUpdatedAsientos));
    }

    @Test
    @Transactional
    void patchNonExistingAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asientosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asientosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asientosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsientos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asientos.setId(longCount.incrementAndGet());

        // Create the Asientos
        AsientosDTO asientosDTO = asientosMapper.toDto(asientos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsientosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(asientosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asientos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsientos() throws Exception {
        // Initialize the database
        insertedAsientos = asientosRepository.saveAndFlush(asientos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asientos
        restAsientosMockMvc
            .perform(delete(ENTITY_API_URL_ID, asientos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return asientosRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Asientos getPersistedAsientos(Asientos asientos) {
        return asientosRepository.findById(asientos.getId()).orElseThrow();
    }

    protected void assertPersistedAsientosToMatchAllProperties(Asientos expectedAsientos) {
        assertAsientosAllPropertiesEquals(expectedAsientos, getPersistedAsientos(expectedAsientos));
    }

    protected void assertPersistedAsientosToMatchUpdatableProperties(Asientos expectedAsientos) {
        assertAsientosAllUpdatablePropertiesEquals(expectedAsientos, getPersistedAsientos(expectedAsientos));
    }
}
