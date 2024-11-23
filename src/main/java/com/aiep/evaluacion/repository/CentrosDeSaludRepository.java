package com.aiep.evaluacion.repository;

import com.aiep.evaluacion.domain.CentrosDeSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentrosDeSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentrosDeSaludRepository extends JpaRepository<CentrosDeSalud, Long> {}
