package com.aiep.evaluacion.repository;

import com.aiep.evaluacion.domain.Medicos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Medicos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicosRepository extends JpaRepository<Medicos, Long> {}
