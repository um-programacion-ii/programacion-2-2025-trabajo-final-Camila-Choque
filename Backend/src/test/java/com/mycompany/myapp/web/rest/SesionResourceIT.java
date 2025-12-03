package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.SesionAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.repository.SesionRepository;
import com.mycompany.myapp.service.dto.SesionDTO;
import com.mycompany.myapp.service.mapper.SesionMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SesionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SesionResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_FLUJO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_FLUJO = "BBBBBBBBBB";

    private static final Long DEFAULT_EVENTO_SELECCIONADO = 1L;
    private static final Long UPDATED_EVENTO_SELECCIONADO = 2L;

    private static final LocalDate DEFAULT_ULTIMA_ACTIVIDAD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ULTIMA_ACTIVIDAD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRA_EN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRA_EN = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/sesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private SesionMapper sesionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSesionMockMvc;

    private Sesion sesion;

    private Sesion insertedSesion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sesion createEntity() {
        return new Sesion()
            .token(DEFAULT_TOKEN)
            .estadoFlujo(DEFAULT_ESTADO_FLUJO)
            .eventoSeleccionado(DEFAULT_EVENTO_SELECCIONADO)
            .ultimaActividad(DEFAULT_ULTIMA_ACTIVIDAD)
            .expiraEn(DEFAULT_EXPIRA_EN);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sesion createUpdatedEntity() {
        return new Sesion()
            .token(UPDATED_TOKEN)
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .eventoSeleccionado(UPDATED_EVENTO_SELECCIONADO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .expiraEn(UPDATED_EXPIRA_EN);
    }

    @BeforeEach
    void initTest() {
        sesion = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSesion != null) {
            sesionRepository.delete(insertedSesion);
            insertedSesion = null;
        }
    }

    @Test
    @Transactional
    void createSesion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);
        var returnedSesionDTO = om.readValue(
            restSesionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SesionDTO.class
        );

        // Validate the Sesion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSesion = sesionMapper.toEntity(returnedSesionDTO);
        assertSesionUpdatableFieldsEquals(returnedSesion, getPersistedSesion(returnedSesion));

        insertedSesion = returnedSesion;
    }

    @Test
    @Transactional
    void createSesionWithExistingId() throws Exception {
        // Create the Sesion with an existing ID
        sesion.setId(1L);
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSesionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSesions() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        // Get all the sesionList
        restSesionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sesion.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].estadoFlujo").value(hasItem(DEFAULT_ESTADO_FLUJO)))
            .andExpect(jsonPath("$.[*].eventoSeleccionado").value(hasItem(DEFAULT_EVENTO_SELECCIONADO.intValue())))
            .andExpect(jsonPath("$.[*].ultimaActividad").value(hasItem(DEFAULT_ULTIMA_ACTIVIDAD.toString())))
            .andExpect(jsonPath("$.[*].expiraEn").value(hasItem(DEFAULT_EXPIRA_EN.toString())));
    }

    @Test
    @Transactional
    void getSesion() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        // Get the sesion
        restSesionMockMvc
            .perform(get(ENTITY_API_URL_ID, sesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sesion.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.estadoFlujo").value(DEFAULT_ESTADO_FLUJO))
            .andExpect(jsonPath("$.eventoSeleccionado").value(DEFAULT_EVENTO_SELECCIONADO.intValue()))
            .andExpect(jsonPath("$.ultimaActividad").value(DEFAULT_ULTIMA_ACTIVIDAD.toString()))
            .andExpect(jsonPath("$.expiraEn").value(DEFAULT_EXPIRA_EN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSesion() throws Exception {
        // Get the sesion
        restSesionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSesion() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesion
        Sesion updatedSesion = sesionRepository.findById(sesion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSesion are not directly saved in db
        em.detach(updatedSesion);
        updatedSesion
            .token(UPDATED_TOKEN)
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .eventoSeleccionado(UPDATED_EVENTO_SELECCIONADO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .expiraEn(UPDATED_EXPIRA_EN);
        SesionDTO sesionDTO = sesionMapper.toDto(updatedSesion);

        restSesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSesionToMatchAllProperties(updatedSesion);
    }

    @Test
    @Transactional
    void putNonExistingSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSesionWithPatch() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesion using partial update
        Sesion partialUpdatedSesion = new Sesion();
        partialUpdatedSesion.setId(sesion.getId());

        partialUpdatedSesion.estadoFlujo(UPDATED_ESTADO_FLUJO).ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD);

        restSesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesion))
            )
            .andExpect(status().isOk());

        // Validate the Sesion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSesion, sesion), getPersistedSesion(sesion));
    }

    @Test
    @Transactional
    void fullUpdateSesionWithPatch() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesion using partial update
        Sesion partialUpdatedSesion = new Sesion();
        partialUpdatedSesion.setId(sesion.getId());

        partialUpdatedSesion
            .token(UPDATED_TOKEN)
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .eventoSeleccionado(UPDATED_EVENTO_SELECCIONADO)
            .ultimaActividad(UPDATED_ULTIMA_ACTIVIDAD)
            .expiraEn(UPDATED_EXPIRA_EN);

        restSesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesion))
            )
            .andExpect(status().isOk());

        // Validate the Sesion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUpdatableFieldsEquals(partialUpdatedSesion, getPersistedSesion(partialUpdatedSesion));
    }

    @Test
    @Transactional
    void patchNonExistingSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sesionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSesion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesion.setId(longCount.incrementAndGet());

        // Create the Sesion
        SesionDTO sesionDTO = sesionMapper.toDto(sesion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sesionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sesion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSesion() throws Exception {
        // Initialize the database
        insertedSesion = sesionRepository.saveAndFlush(sesion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sesion
        restSesionMockMvc
            .perform(delete(ENTITY_API_URL_ID, sesion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sesionRepository.count();
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

    protected Sesion getPersistedSesion(Sesion sesion) {
        return sesionRepository.findById(sesion.getId()).orElseThrow();
    }

    protected void assertPersistedSesionToMatchAllProperties(Sesion expectedSesion) {
        assertSesionAllPropertiesEquals(expectedSesion, getPersistedSesion(expectedSesion));
    }

    protected void assertPersistedSesionToMatchUpdatableProperties(Sesion expectedSesion) {
        assertSesionAllUpdatablePropertiesEquals(expectedSesion, getPersistedSesion(expectedSesion));
    }
}
