package com.aiep.evaluacion.repository;

import com.aiep.evaluacion.domain.Agendamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agendamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendamientoRepository extends JpaRepository<Agendamiento, Long> {}
