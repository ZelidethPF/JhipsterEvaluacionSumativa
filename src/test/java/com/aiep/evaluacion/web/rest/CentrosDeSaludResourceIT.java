package com.aiep.evaluacion.web.rest;

import static com.aiep.evaluacion.domain.CentrosDeSaludAsserts.*;
import static com.aiep.evaluacion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.evaluacion.IntegrationTest;
import com.aiep.evaluacion.domain.CentrosDeSalud;
import com.aiep.evaluacion.repository.CentrosDeSaludRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Integration tests for the {@link CentrosDeSaludResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentrosDeSaludResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centros-de-saluds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentrosDeSaludRepository centrosDeSaludRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentrosDeSaludMockMvc;

    private CentrosDeSalud centrosDeSalud;

    private CentrosDeSalud insertedCentrosDeSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentrosDeSalud createEntity() {
        return new CentrosDeSalud()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .ciudad(DEFAULT_CIUDAD)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .email(DEFAULT_EMAIL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentrosDeSalud createUpdatedEntity() {
        return new CentrosDeSalud()
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL);
    }

    @BeforeEach
    public void initTest() {
        centrosDeSalud = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentrosDeSalud != null) {
            centrosDeSaludRepository.delete(insertedCentrosDeSalud);
            insertedCentrosDeSalud = null;
        }
    }

    @Test
    @Transactional
    void createCentrosDeSalud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentrosDeSalud
        var returnedCentrosDeSalud = om.readValue(
            restCentrosDeSaludMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosDeSalud)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentrosDeSalud.class
        );

        // Validate the CentrosDeSalud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCentrosDeSaludUpdatableFieldsEquals(returnedCentrosDeSalud, getPersistedCentrosDeSalud(returnedCentrosDeSalud));

        insertedCentrosDeSalud = returnedCentrosDeSalud;
    }

    @Test
    @Transactional
    void createCentrosDeSaludWithExistingId() throws Exception {
        // Create the CentrosDeSalud with an existing ID
        centrosDeSalud.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentrosDeSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosDeSalud)))
            .andExpect(status().isBadRequest());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCentrosDeSaluds() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        // Get all the centrosDeSaludList
        restCentrosDeSaludMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centrosDeSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getCentrosDeSalud() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        // Get the centrosDeSalud
        restCentrosDeSaludMockMvc
            .perform(get(ENTITY_API_URL_ID, centrosDeSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centrosDeSalud.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingCentrosDeSalud() throws Exception {
        // Get the centrosDeSalud
        restCentrosDeSaludMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentrosDeSalud() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosDeSalud
        CentrosDeSalud updatedCentrosDeSalud = centrosDeSaludRepository.findById(centrosDeSalud.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentrosDeSalud are not directly saved in db
        em.detach(updatedCentrosDeSalud);
        updatedCentrosDeSalud
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL);

        restCentrosDeSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCentrosDeSalud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCentrosDeSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentrosDeSaludToMatchAllProperties(updatedCentrosDeSalud);
    }

    @Test
    @Transactional
    void putNonExistingCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centrosDeSalud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centrosDeSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centrosDeSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centrosDeSalud)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentrosDeSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosDeSalud using partial update
        CentrosDeSalud partialUpdatedCentrosDeSalud = new CentrosDeSalud();
        partialUpdatedCentrosDeSalud.setId(centrosDeSalud.getId());

        partialUpdatedCentrosDeSalud.nombre(UPDATED_NOMBRE).direccion(UPDATED_DIRECCION);

        restCentrosDeSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentrosDeSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentrosDeSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosDeSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentrosDeSaludUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentrosDeSalud, centrosDeSalud),
            getPersistedCentrosDeSalud(centrosDeSalud)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentrosDeSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centrosDeSalud using partial update
        CentrosDeSalud partialUpdatedCentrosDeSalud = new CentrosDeSalud();
        partialUpdatedCentrosDeSalud.setId(centrosDeSalud.getId());

        partialUpdatedCentrosDeSalud
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .email(UPDATED_EMAIL);

        restCentrosDeSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentrosDeSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentrosDeSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentrosDeSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentrosDeSaludUpdatableFieldsEquals(partialUpdatedCentrosDeSalud, getPersistedCentrosDeSalud(partialUpdatedCentrosDeSalud));
    }

    @Test
    @Transactional
    void patchNonExistingCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centrosDeSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centrosDeSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centrosDeSalud))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentrosDeSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centrosDeSalud.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentrosDeSaludMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centrosDeSalud)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentrosDeSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentrosDeSalud() throws Exception {
        // Initialize the database
        insertedCentrosDeSalud = centrosDeSaludRepository.saveAndFlush(centrosDeSalud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centrosDeSalud
        restCentrosDeSaludMockMvc
            .perform(delete(ENTITY_API_URL_ID, centrosDeSalud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centrosDeSaludRepository.count();
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

    protected CentrosDeSalud getPersistedCentrosDeSalud(CentrosDeSalud centrosDeSalud) {
        return centrosDeSaludRepository.findById(centrosDeSalud.getId()).orElseThrow();
    }

    protected void assertPersistedCentrosDeSaludToMatchAllProperties(CentrosDeSalud expectedCentrosDeSalud) {
        assertCentrosDeSaludAllPropertiesEquals(expectedCentrosDeSalud, getPersistedCentrosDeSalud(expectedCentrosDeSalud));
    }

    protected void assertPersistedCentrosDeSaludToMatchUpdatableProperties(CentrosDeSalud expectedCentrosDeSalud) {
        assertCentrosDeSaludAllUpdatablePropertiesEquals(expectedCentrosDeSalud, getPersistedCentrosDeSalud(expectedCentrosDeSalud));
    }
}
