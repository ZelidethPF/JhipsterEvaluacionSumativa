package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.domain.Agendamiento;
import com.aiep.evaluacion.repository.AgendamientoRepository;
import com.aiep.evaluacion.service.AgendamientoService;
import com.aiep.evaluacion.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.aiep.evaluacion.domain.Agendamiento}.
 */
@RestController
@RequestMapping("/api/agendamientos")
public class AgendamientoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AgendamientoResource.class);

    private static final String ENTITY_NAME = "agendamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendamientoService agendamientoService;

    private final AgendamientoRepository agendamientoRepository;

    public AgendamientoResource(AgendamientoService agendamientoService, AgendamientoRepository agendamientoRepository) {
        this.agendamientoService = agendamientoService;
        this.agendamientoRepository = agendamientoRepository;
    }

    /**
     * {@code POST  /agendamientos} : Create a new agendamiento.
     *
     * @param agendamiento the agendamiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendamiento, or with status {@code 400 (Bad Request)} if the agendamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Agendamiento> createAgendamiento(@RequestBody Agendamiento agendamiento) throws URISyntaxException {
        LOG.debug("REST request to save Agendamiento : {}", agendamiento);
        if (agendamiento.getId() != null) {
            throw new BadRequestAlertException("A new agendamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendamiento = agendamientoService.save(agendamiento);
        return ResponseEntity.created(new URI("/api/agendamientos/" + agendamiento.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agendamiento.getId().toString()))
            .body(agendamiento);
    }

    /**
     * {@code PUT  /agendamientos/:id} : Updates an existing agendamiento.
     *
     * @param id the id of the agendamiento to save.
     * @param agendamiento the agendamiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendamiento,
     * or with status {@code 400 (Bad Request)} if the agendamiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendamiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Agendamiento> updateAgendamiento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Agendamiento agendamiento
    ) throws URISyntaxException {
        LOG.debug("REST request to update Agendamiento : {}, {}", id, agendamiento);
        if (agendamiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendamiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendamientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendamiento = agendamientoService.update(agendamiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendamiento.getId().toString()))
            .body(agendamiento);
    }

    /**
     * {@code PATCH  /agendamientos/:id} : Partial updates given fields of an existing agendamiento, field will ignore if it is null
     *
     * @param id the id of the agendamiento to save.
     * @param agendamiento the agendamiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendamiento,
     * or with status {@code 400 (Bad Request)} if the agendamiento is not valid,
     * or with status {@code 404 (Not Found)} if the agendamiento is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendamiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Agendamiento> partialUpdateAgendamiento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Agendamiento agendamiento
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Agendamiento partially : {}, {}", id, agendamiento);
        if (agendamiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendamiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendamientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Agendamiento> result = agendamientoService.partialUpdate(agendamiento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendamiento.getId().toString())
        );
    }

    /**
     * {@code GET  /agendamientos} : get all the agendamientos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendamientos in body.
     */
    @GetMapping("")
    public List<Agendamiento> getAllAgendamientos() {
        LOG.debug("REST request to get all Agendamientos");
        return agendamientoService.findAll();
    }

    /**
     * {@code GET  /agendamientos/:id} : get the "id" agendamiento.
     *
     * @param id the id of the agendamiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendamiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Agendamiento> getAgendamiento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Agendamiento : {}", id);
        Optional<Agendamiento> agendamiento = agendamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendamiento);
    }

    /**
     * {@code DELETE  /agendamientos/:id} : delete the "id" agendamiento.
     *
     * @param id the id of the agendamiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamiento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Agendamiento : {}", id);
        agendamientoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
