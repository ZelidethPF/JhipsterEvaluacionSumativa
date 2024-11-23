package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.domain.Medicos;
import com.aiep.evaluacion.repository.MedicosRepository;
import com.aiep.evaluacion.service.MedicosService;
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
 * REST controller for managing {@link com.aiep.evaluacion.domain.Medicos}.
 */
@RestController
@RequestMapping("/api/medicos")
public class MedicosResource {

    private static final Logger LOG = LoggerFactory.getLogger(MedicosResource.class);

    private static final String ENTITY_NAME = "medicos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicosService medicosService;

    private final MedicosRepository medicosRepository;

    public MedicosResource(MedicosService medicosService, MedicosRepository medicosRepository) {
        this.medicosService = medicosService;
        this.medicosRepository = medicosRepository;
    }

    /**
     * {@code POST  /medicos} : Create a new medicos.
     *
     * @param medicos the medicos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicos, or with status {@code 400 (Bad Request)} if the medicos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Medicos> createMedicos(@RequestBody Medicos medicos) throws URISyntaxException {
        LOG.debug("REST request to save Medicos : {}", medicos);
        if (medicos.getId() != null) {
            throw new BadRequestAlertException("A new medicos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        medicos = medicosService.save(medicos);
        return ResponseEntity.created(new URI("/api/medicos/" + medicos.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, medicos.getId().toString()))
            .body(medicos);
    }

    /**
     * {@code PUT  /medicos/:id} : Updates an existing medicos.
     *
     * @param id the id of the medicos to save.
     * @param medicos the medicos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicos,
     * or with status {@code 400 (Bad Request)} if the medicos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medicos> updateMedicos(@PathVariable(value = "id", required = false) final Long id, @RequestBody Medicos medicos)
        throws URISyntaxException {
        LOG.debug("REST request to update Medicos : {}, {}", id, medicos);
        if (medicos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        medicos = medicosService.update(medicos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicos.getId().toString()))
            .body(medicos);
    }

    /**
     * {@code PATCH  /medicos/:id} : Partial updates given fields of an existing medicos, field will ignore if it is null
     *
     * @param id the id of the medicos to save.
     * @param medicos the medicos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicos,
     * or with status {@code 400 (Bad Request)} if the medicos is not valid,
     * or with status {@code 404 (Not Found)} if the medicos is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Medicos> partialUpdateMedicos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Medicos medicos
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Medicos partially : {}, {}", id, medicos);
        if (medicos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Medicos> result = medicosService.partialUpdate(medicos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicos.getId().toString())
        );
    }

    /**
     * {@code GET  /medicos} : get all the medicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicos in body.
     */
    @GetMapping("")
    public List<Medicos> getAllMedicos() {
        LOG.debug("REST request to get all Medicos");
        return medicosService.findAll();
    }

    /**
     * {@code GET  /medicos/:id} : get the "id" medicos.
     *
     * @param id the id of the medicos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicos> getMedicos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Medicos : {}", id);
        Optional<Medicos> medicos = medicosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicos);
    }

    /**
     * {@code DELETE  /medicos/:id} : delete the "id" medicos.
     *
     * @param id the id of the medicos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Medicos : {}", id);
        medicosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
