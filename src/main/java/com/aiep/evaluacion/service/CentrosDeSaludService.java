package com.aiep.evaluacion.service;

import com.aiep.evaluacion.domain.CentrosDeSalud;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.CentrosDeSalud}.
 */
public interface CentrosDeSaludService {
    /**
     * Save a centrosDeSalud.
     *
     * @param centrosDeSalud the entity to save.
     * @return the persisted entity.
     */
    CentrosDeSalud save(CentrosDeSalud centrosDeSalud);

    /**
     * Updates a centrosDeSalud.
     *
     * @param centrosDeSalud the entity to update.
     * @return the persisted entity.
     */
    CentrosDeSalud update(CentrosDeSalud centrosDeSalud);

    /**
     * Partially updates a centrosDeSalud.
     *
     * @param centrosDeSalud the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CentrosDeSalud> partialUpdate(CentrosDeSalud centrosDeSalud);

    /**
     * Get all the centrosDeSaluds.
     *
     * @return the list of entities.
     */
    List<CentrosDeSalud> findAll();

    /**
     * Get the "id" centrosDeSalud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentrosDeSalud> findOne(Long id);

    /**
     * Delete the "id" centrosDeSalud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
