package com.aiep.evaluacion.web.rest;

import static com.aiep.evaluacion.domain.AgendamientoAsserts.*;
import static com.aiep.evaluacion.web.rest.TestUtil.createUpdateProxyForBean;
import static com.aiep.evaluacion.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.evaluacion.IntegrationTest;
import com.aiep.evaluacion.domain.Agendamiento;
import com.aiep.evaluacion.repository.AgendamientoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link AgendamientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgendamientoResourceIT {

    private static final String DEFAULT_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_CENTRO = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHAHORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHAHORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHAINGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAINGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NIVELPRIORIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NIVELPRIORIDAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agendamientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendamientoRepository agendamientoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendamientoMockMvc;

    private Agendamiento agendamiento;

    private Agendamiento insertedAgendamiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamiento createEntity() {
        return new Agendamiento()
            .paciente(DEFAULT_PACIENTE)
            .medico(DEFAULT_MEDICO)
            .centro(DEFAULT_CENTRO)
            .fechahora(DEFAULT_FECHAHORA)
            .estado(DEFAULT_ESTADO)
            .origen(DEFAULT_ORIGEN)
            .motivo(DEFAULT_MOTIVO)
            .fechaingreso(DEFAULT_FECHAINGRESO)
            .nivelprioridad(DEFAULT_NIVELPRIORIDAD);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agendamiento createUpdatedEntity() {
        return new Agendamiento()
            .paciente(UPDATED_PACIENTE)
            .medico(UPDATED_MEDICO)
            .centro(UPDATED_CENTRO)
            .fechahora(UPDATED_FECHAHORA)
            .estado(UPDATED_ESTADO)
            .origen(UPDATED_ORIGEN)
            .motivo(UPDATED_MOTIVO)
            .fechaingreso(UPDATED_FECHAINGRESO)
            .nivelprioridad(UPDATED_NIVELPRIORIDAD);
    }

    @BeforeEach
    public void initTest() {
        agendamiento = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgendamiento != null) {
            agendamientoRepository.delete(insertedAgendamiento);
            insertedAgendamiento = null;
        }
    }

    @Test
    @Transactional
    void createAgendamiento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agendamiento
        var returnedAgendamiento = om.readValue(
            restAgendamientoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamiento)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Agendamiento.class
        );

        // Validate the Agendamiento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendamientoUpdatableFieldsEquals(returnedAgendamiento, getPersistedAgendamiento(returnedAgendamiento));

        insertedAgendamiento = returnedAgendamiento;
    }

    @Test
    @Transactional
    void createAgendamientoWithExistingId() throws Exception {
        // Create the Agendamiento with an existing ID
        agendamiento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendamientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendamientos() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        // Get all the agendamientoList
        restAgendamientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].paciente").value(hasItem(DEFAULT_PACIENTE)))
            .andExpect(jsonPath("$.[*].medico").value(hasItem(DEFAULT_MEDICO)))
            .andExpect(jsonPath("$.[*].centro").value(hasItem(DEFAULT_CENTRO)))
            .andExpect(jsonPath("$.[*].fechahora").value(hasItem(sameInstant(DEFAULT_FECHAHORA))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)))
            .andExpect(jsonPath("$.[*].fechaingreso").value(hasItem(DEFAULT_FECHAINGRESO.toString())))
            .andExpect(jsonPath("$.[*].nivelprioridad").value(hasItem(DEFAULT_NIVELPRIORIDAD)));
    }

    @Test
    @Transactional
    void getAgendamiento() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        // Get the agendamiento
        restAgendamientoMockMvc
            .perform(get(ENTITY_API_URL_ID, agendamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendamiento.getId().intValue()))
            .andExpect(jsonPath("$.paciente").value(DEFAULT_PACIENTE))
            .andExpect(jsonPath("$.medico").value(DEFAULT_MEDICO))
            .andExpect(jsonPath("$.centro").value(DEFAULT_CENTRO))
            .andExpect(jsonPath("$.fechahora").value(sameInstant(DEFAULT_FECHAHORA)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO))
            .andExpect(jsonPath("$.fechaingreso").value(DEFAULT_FECHAINGRESO.toString()))
            .andExpect(jsonPath("$.nivelprioridad").value(DEFAULT_NIVELPRIORIDAD));
    }

    @Test
    @Transactional
    void getNonExistingAgendamiento() throws Exception {
        // Get the agendamiento
        restAgendamientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendamiento() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamiento
        Agendamiento updatedAgendamiento = agendamientoRepository.findById(agendamiento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgendamiento are not directly saved in db
        em.detach(updatedAgendamiento);
        updatedAgendamiento
            .paciente(UPDATED_PACIENTE)
            .medico(UPDATED_MEDICO)
            .centro(UPDATED_CENTRO)
            .fechahora(UPDATED_FECHAHORA)
            .estado(UPDATED_ESTADO)
            .origen(UPDATED_ORIGEN)
            .motivo(UPDATED_MOTIVO)
            .fechaingreso(UPDATED_FECHAINGRESO)
            .nivelprioridad(UPDATED_NIVELPRIORIDAD);

        restAgendamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendamiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgendamiento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendamientoToMatchAllProperties(updatedAgendamiento);
    }

    @Test
    @Transactional
    void putNonExistingAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendamiento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendamiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendamiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendamiento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendamientoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamiento using partial update
        Agendamiento partialUpdatedAgendamiento = new Agendamiento();
        partialUpdatedAgendamiento.setId(agendamiento.getId());

        partialUpdatedAgendamiento
            .fechahora(UPDATED_FECHAHORA)
            .estado(UPDATED_ESTADO)
            .motivo(UPDATED_MOTIVO)
            .nivelprioridad(UPDATED_NIVELPRIORIDAD);

        restAgendamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendamiento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendamientoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendamiento, agendamiento),
            getPersistedAgendamiento(agendamiento)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendamientoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendamiento using partial update
        Agendamiento partialUpdatedAgendamiento = new Agendamiento();
        partialUpdatedAgendamiento.setId(agendamiento.getId());

        partialUpdatedAgendamiento
            .paciente(UPDATED_PACIENTE)
            .medico(UPDATED_MEDICO)
            .centro(UPDATED_CENTRO)
            .fechahora(UPDATED_FECHAHORA)
            .estado(UPDATED_ESTADO)
            .origen(UPDATED_ORIGEN)
            .motivo(UPDATED_MOTIVO)
            .fechaingreso(UPDATED_FECHAINGRESO)
            .nivelprioridad(UPDATED_NIVELPRIORIDAD);

        restAgendamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendamiento))
            )
            .andExpect(status().isOk());

        // Validate the Agendamiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendamientoUpdatableFieldsEquals(partialUpdatedAgendamiento, getPersistedAgendamiento(partialUpdatedAgendamiento));
    }

    @Test
    @Transactional
    void patchNonExistingAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendamiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendamiento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendamiento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendamientoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agendamiento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agendamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendamiento() throws Exception {
        // Initialize the database
        insertedAgendamiento = agendamientoRepository.saveAndFlush(agendamiento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendamiento
        restAgendamientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendamiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendamientoRepository.count();
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

    protected Agendamiento getPersistedAgendamiento(Agendamiento agendamiento) {
        return agendamientoRepository.findById(agendamiento.getId()).orElseThrow();
    }

    protected void assertPersistedAgendamientoToMatchAllProperties(Agendamiento expectedAgendamiento) {
        assertAgendamientoAllPropertiesEquals(expectedAgendamiento, getPersistedAgendamiento(expectedAgendamiento));
    }

    protected void assertPersistedAgendamientoToMatchUpdatableProperties(Agendamiento expectedAgendamiento) {
        assertAgendamientoAllUpdatablePropertiesEquals(expectedAgendamiento, getPersistedAgendamiento(expectedAgendamiento));
    }
}
