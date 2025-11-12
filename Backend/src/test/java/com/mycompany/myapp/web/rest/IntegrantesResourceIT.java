package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.IntegrantesAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Integrantes;
import com.mycompany.myapp.repository.IntegrantesRepository;
import com.mycompany.myapp.service.dto.IntegrantesDTO;
import com.mycompany.myapp.service.mapper.IntegrantesMapper;
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
 * Integration tests for the {@link IntegrantesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntegrantesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/integrantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntegrantesRepository integrantesRepository;

    @Autowired
    private IntegrantesMapper integrantesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntegrantesMockMvc;

    private Integrantes integrantes;

    private Integrantes insertedIntegrantes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Integrantes createEntity() {
        return new Integrantes().nombre(DEFAULT_NOMBRE).apellido(DEFAULT_APELLIDO).identificacion(DEFAULT_IDENTIFICACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Integrantes createUpdatedEntity() {
        return new Integrantes().nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).identificacion(UPDATED_IDENTIFICACION);
    }

    @BeforeEach
    void initTest() {
        integrantes = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIntegrantes != null) {
            integrantesRepository.delete(insertedIntegrantes);
            insertedIntegrantes = null;
        }
    }

    @Test
    @Transactional
    void createIntegrantes() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);
        var returnedIntegrantesDTO = om.readValue(
            restIntegrantesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrantesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntegrantesDTO.class
        );

        // Validate the Integrantes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedIntegrantes = integrantesMapper.toEntity(returnedIntegrantesDTO);
        assertIntegrantesUpdatableFieldsEquals(returnedIntegrantes, getPersistedIntegrantes(returnedIntegrantes));

        insertedIntegrantes = returnedIntegrantes;
    }

    @Test
    @Transactional
    void createIntegrantesWithExistingId() throws Exception {
        // Create the Integrantes with an existing ID
        integrantes.setId(1L);
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrantesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrantesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntegrantes() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        // Get all the integrantesList
        restIntegrantesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrantes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)));
    }

    @Test
    @Transactional
    void getIntegrantes() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        // Get the integrantes
        restIntegrantesMockMvc
            .perform(get(ENTITY_API_URL_ID, integrantes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(integrantes.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION));
    }

    @Test
    @Transactional
    void getNonExistingIntegrantes() throws Exception {
        // Get the integrantes
        restIntegrantesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntegrantes() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrantes
        Integrantes updatedIntegrantes = integrantesRepository.findById(integrantes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIntegrantes are not directly saved in db
        em.detach(updatedIntegrantes);
        updatedIntegrantes.nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).identificacion(UPDATED_IDENTIFICACION);
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(updatedIntegrantes);

        restIntegrantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrantesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrantesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntegrantesToMatchAllProperties(updatedIntegrantes);
    }

    @Test
    @Transactional
    void putNonExistingIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrantesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrantesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(integrantesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(integrantesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntegrantesWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrantes using partial update
        Integrantes partialUpdatedIntegrantes = new Integrantes();
        partialUpdatedIntegrantes.setId(integrantes.getId());

        partialUpdatedIntegrantes.nombre(UPDATED_NOMBRE).identificacion(UPDATED_IDENTIFICACION);

        restIntegrantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrantes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrantes))
            )
            .andExpect(status().isOk());

        // Validate the Integrantes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrantesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntegrantes, integrantes),
            getPersistedIntegrantes(integrantes)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntegrantesWithPatch() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the integrantes using partial update
        Integrantes partialUpdatedIntegrantes = new Integrantes();
        partialUpdatedIntegrantes.setId(integrantes.getId());

        partialUpdatedIntegrantes.nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).identificacion(UPDATED_IDENTIFICACION);

        restIntegrantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrantes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntegrantes))
            )
            .andExpect(status().isOk());

        // Validate the Integrantes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntegrantesUpdatableFieldsEquals(partialUpdatedIntegrantes, getPersistedIntegrantes(partialUpdatedIntegrantes));
    }

    @Test
    @Transactional
    void patchNonExistingIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, integrantesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrantesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(integrantesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntegrantes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        integrantes.setId(longCount.incrementAndGet());

        // Create the Integrantes
        IntegrantesDTO integrantesDTO = integrantesMapper.toDto(integrantes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrantesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(integrantesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Integrantes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntegrantes() throws Exception {
        // Initialize the database
        insertedIntegrantes = integrantesRepository.saveAndFlush(integrantes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the integrantes
        restIntegrantesMockMvc
            .perform(delete(ENTITY_API_URL_ID, integrantes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return integrantesRepository.count();
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

    protected Integrantes getPersistedIntegrantes(Integrantes integrantes) {
        return integrantesRepository.findById(integrantes.getId()).orElseThrow();
    }

    protected void assertPersistedIntegrantesToMatchAllProperties(Integrantes expectedIntegrantes) {
        assertIntegrantesAllPropertiesEquals(expectedIntegrantes, getPersistedIntegrantes(expectedIntegrantes));
    }

    protected void assertPersistedIntegrantesToMatchUpdatableProperties(Integrantes expectedIntegrantes) {
        assertIntegrantesAllUpdatablePropertiesEquals(expectedIntegrantes, getPersistedIntegrantes(expectedIntegrantes));
    }
}
