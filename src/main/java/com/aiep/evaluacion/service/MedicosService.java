package com.aiep.evaluacion.service;

import com.aiep.evaluacion.domain.Medicos;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Medicos}.
 */
public interface MedicosService {
    /**
     * Save a medicos.
     *
     * @param medicos the entity to save.
     * @return the persisted entity.
     */
    Medicos save(Medicos medicos);

    /**
     * Updates a medicos.
     *
     * @param medicos the entity to update.
     * @return the persisted entity.
     */
    Medicos update(Medicos medicos);

    /**
     * Partially updates a medicos.
     *
     * @param medicos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Medicos> partialUpdate(Medicos medicos);

    /**
     * Get all the medicos.
     *
     * @return the list of entities.
     */
    List<Medicos> findAll();

    /**
     * Get the "id" medicos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Medicos> findOne(Long id);

    /**
     * Delete the "id" medicos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
