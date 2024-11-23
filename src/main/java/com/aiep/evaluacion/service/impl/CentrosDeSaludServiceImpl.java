package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.CentrosDeSalud;
import com.aiep.evaluacion.repository.CentrosDeSaludRepository;
import com.aiep.evaluacion.service.CentrosDeSaludService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.CentrosDeSalud}.
 */
@Service
@Transactional
public class CentrosDeSaludServiceImpl implements CentrosDeSaludService {

    private static final Logger LOG = LoggerFactory.getLogger(CentrosDeSaludServiceImpl.class);

    private final CentrosDeSaludRepository centrosDeSaludRepository;

    public CentrosDeSaludServiceImpl(CentrosDeSaludRepository centrosDeSaludRepository) {
        this.centrosDeSaludRepository = centrosDeSaludRepository;
    }

    @Override
    public CentrosDeSalud save(CentrosDeSalud centrosDeSalud) {
        LOG.debug("Request to save CentrosDeSalud : {}", centrosDeSalud);
        return centrosDeSaludRepository.save(centrosDeSalud);
    }

    @Override
    public CentrosDeSalud update(CentrosDeSalud centrosDeSalud) {
        LOG.debug("Request to update CentrosDeSalud : {}", centrosDeSalud);
        return centrosDeSaludRepository.save(centrosDeSalud);
    }

    @Override
    public Optional<CentrosDeSalud> partialUpdate(CentrosDeSalud centrosDeSalud) {
        LOG.debug("Request to partially update CentrosDeSalud : {}", centrosDeSalud);

        return centrosDeSaludRepository
            .findById(centrosDeSalud.getId())
            .map(existingCentrosDeSalud -> {
                if (centrosDeSalud.getNombre() != null) {
                    existingCentrosDeSalud.setNombre(centrosDeSalud.getNombre());
                }
                if (centrosDeSalud.getTipo() != null) {
                    existingCentrosDeSalud.setTipo(centrosDeSalud.getTipo());
                }
                if (centrosDeSalud.getCiudad() != null) {
                    existingCentrosDeSalud.setCiudad(centrosDeSalud.getCiudad());
                }
                if (centrosDeSalud.getDireccion() != null) {
                    existingCentrosDeSalud.setDireccion(centrosDeSalud.getDireccion());
                }
                if (centrosDeSalud.getTelefono() != null) {
                    existingCentrosDeSalud.setTelefono(centrosDeSalud.getTelefono());
                }
                if (centrosDeSalud.getEmail() != null) {
                    existingCentrosDeSalud.setEmail(centrosDeSalud.getEmail());
                }

                return existingCentrosDeSalud;
            })
            .map(centrosDeSaludRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentrosDeSalud> findAll() {
        LOG.debug("Request to get all CentrosDeSaluds");
        return centrosDeSaludRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentrosDeSalud> findOne(Long id) {
        LOG.debug("Request to get CentrosDeSalud : {}", id);
        return centrosDeSaludRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CentrosDeSalud : {}", id);
        centrosDeSaludRepository.deleteById(id);
    }
}
