package com.aiep.evaluacion.service;

import com.aiep.evaluacion.domain.Paciente;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Paciente}.
 */
public interface PacienteService {
    /**
     * Save a paciente.
     *
     * @param paciente the entity to save.
     * @return the persisted entity.
     */
    Paciente save(Paciente paciente);

    /**
     * Updates a paciente.
     *
     * @param paciente the entity to update.
     * @return the persisted entity.
     */
    Paciente update(Paciente paciente);

    /**
     * Partially updates a paciente.
     *
     * @param paciente the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Paciente> partialUpdate(Paciente paciente);

    /**
     * Get all the pacientes.
     *
     * @return the list of entities.
     */
    List<Paciente> findAll();

    /**
     * Get the "id" paciente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paciente> findOne(Long id);

    /**
     * Delete the "id" paciente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
