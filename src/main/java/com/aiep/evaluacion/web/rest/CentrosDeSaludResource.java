package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.domain.CentrosDeSalud;
import com.aiep.evaluacion.repository.CentrosDeSaludRepository;
import com.aiep.evaluacion.service.CentrosDeSaludService;
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
 * REST controller for managing {@link com.aiep.evaluacion.domain.CentrosDeSalud}.
 */
@RestController
@RequestMapping("/api/centros-de-saluds")
public class CentrosDeSaludResource {

    private static final Logger LOG = LoggerFactory.getLogger(CentrosDeSaludResource.class);

    private static final String ENTITY_NAME = "centrosDeSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentrosDeSaludService centrosDeSaludService;

    private final CentrosDeSaludRepository centrosDeSaludRepository;

    public CentrosDeSaludResource(CentrosDeSaludService centrosDeSaludService, CentrosDeSaludRepository centrosDeSaludRepository) {
        this.centrosDeSaludService = centrosDeSaludService;
        this.centrosDeSaludRepository = centrosDeSaludRepository;
    }

    /**
     * {@code POST  /centros-de-saluds} : Create a new centrosDeSalud.
     *
     * @param centrosDeSalud the centrosDeSalud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centrosDeSalud, or with status {@code 400 (Bad Request)} if the centrosDeSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentrosDeSalud> createCentrosDeSalud(@RequestBody CentrosDeSalud centrosDeSalud) throws URISyntaxException {
        LOG.debug("REST request to save CentrosDeSalud : {}", centrosDeSalud);
        if (centrosDeSalud.getId() != null) {
            throw new BadRequestAlertException("A new centrosDeSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centrosDeSalud = centrosDeSaludService.save(centrosDeSalud);
        return ResponseEntity.created(new URI("/api/centros-de-saluds/" + centrosDeSalud.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centrosDeSalud.getId().toString()))
            .body(centrosDeSalud);
    }

    /**
     * {@code PUT  /centros-de-saluds/:id} : Updates an existing centrosDeSalud.
     *
     * @param id the id of the centrosDeSalud to save.
     * @param centrosDeSalud the centrosDeSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centrosDeSalud,
     * or with status {@code 400 (Bad Request)} if the centrosDeSalud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centrosDeSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentrosDeSalud> updateCentrosDeSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentrosDeSalud centrosDeSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to update CentrosDeSalud : {}, {}", id, centrosDeSalud);
        if (centrosDeSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centrosDeSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centrosDeSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centrosDeSalud = centrosDeSaludService.update(centrosDeSalud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centrosDeSalud.getId().toString()))
            .body(centrosDeSalud);
    }

    /**
     * {@code PATCH  /centros-de-saluds/:id} : Partial updates given fields of an existing centrosDeSalud, field will ignore if it is null
     *
     * @param id the id of the centrosDeSalud to save.
     * @param centrosDeSalud the centrosDeSalud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centrosDeSalud,
     * or with status {@code 400 (Bad Request)} if the centrosDeSalud is not valid,
     * or with status {@code 404 (Not Found)} if the centrosDeSalud is not found,
     * or with status {@code 500 (Internal Server Error)} if the centrosDeSalud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentrosDeSalud> partialUpdateCentrosDeSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CentrosDeSalud centrosDeSalud
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CentrosDeSalud partially : {}, {}", id, centrosDeSalud);
        if (centrosDeSalud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centrosDeSalud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centrosDeSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentrosDeSalud> result = centrosDeSaludService.partialUpdate(centrosDeSalud);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centrosDeSalud.getId().toString())
        );
    }

    /**
     * {@code GET  /centros-de-saluds} : get all the centrosDeSaluds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centrosDeSaluds in body.
     */
    @GetMapping("")
    public List<CentrosDeSalud> getAllCentrosDeSaluds() {
        LOG.debug("REST request to get all CentrosDeSaluds");
        return centrosDeSaludService.findAll();
    }

    /**
     * {@code GET  /centros-de-saluds/:id} : get the "id" centrosDeSalud.
     *
     * @param id the id of the centrosDeSalud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centrosDeSalud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentrosDeSalud> getCentrosDeSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CentrosDeSalud : {}", id);
        Optional<CentrosDeSalud> centrosDeSalud = centrosDeSaludService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centrosDeSalud);
    }

    /**
     * {@code DELETE  /centros-de-saluds/:id} : delete the "id" centrosDeSalud.
     *
     * @param id the id of the centrosDeSalud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentrosDeSalud(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CentrosDeSalud : {}", id);
        centrosDeSaludService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
