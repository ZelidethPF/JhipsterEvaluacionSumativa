package com.aiep.evaluacion.service;

import com.aiep.evaluacion.domain.Agendamiento;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Agendamiento}.
 */
public interface AgendamientoService {
    /**
     * Save a agendamiento.
     *
     * @param agendamiento the entity to save.
     * @return the persisted entity.
     */
    Agendamiento save(Agendamiento agendamiento);

    /**
     * Updates a agendamiento.
     *
     * @param agendamiento the entity to update.
     * @return the persisted entity.
     */
    Agendamiento update(Agendamiento agendamiento);

    /**
     * Partially updates a agendamiento.
     *
     * @param agendamiento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Agendamiento> partialUpdate(Agendamiento agendamiento);

    /**
     * Get all the agendamientos.
     *
     * @return the list of entities.
     */
    List<Agendamiento> findAll();

    /**
     * Get the "id" agendamiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agendamiento> findOne(Long id);

    /**
     * Delete the "id" agendamiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
