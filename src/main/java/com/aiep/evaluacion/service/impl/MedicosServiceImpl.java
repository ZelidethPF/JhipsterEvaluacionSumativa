package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Medicos;
import com.aiep.evaluacion.repository.MedicosRepository;
import com.aiep.evaluacion.service.MedicosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Medicos}.
 */
@Service
@Transactional
public class MedicosServiceImpl implements MedicosService {

    private static final Logger LOG = LoggerFactory.getLogger(MedicosServiceImpl.class);

    private final MedicosRepository medicosRepository;

    public MedicosServiceImpl(MedicosRepository medicosRepository) {
        this.medicosRepository = medicosRepository;
    }

    @Override
    public Medicos save(Medicos medicos) {
        LOG.debug("Request to save Medicos : {}", medicos);
        return medicosRepository.save(medicos);
    }

    @Override
    public Medicos update(Medicos medicos) {
        LOG.debug("Request to update Medicos : {}", medicos);
        return medicosRepository.save(medicos);
    }

    @Override
    public Optional<Medicos> partialUpdate(Medicos medicos) {
        LOG.debug("Request to partially update Medicos : {}", medicos);

        return medicosRepository
            .findById(medicos.getId())
            .map(existingMedicos -> {
                if (medicos.getNombre() != null) {
                    existingMedicos.setNombre(medicos.getNombre());
                }
                if (medicos.getRut() != null) {
                    existingMedicos.setRut(medicos.getRut());
                }
                if (medicos.getEspecialidad() != null) {
                    existingMedicos.setEspecialidad(medicos.getEspecialidad());
                }
                if (medicos.getTelefono() != null) {
                    existingMedicos.setTelefono(medicos.getTelefono());
                }
                if (medicos.getCiudad() != null) {
                    existingMedicos.setCiudad(medicos.getCiudad());
                }
                if (medicos.getEmail() != null) {
                    existingMedicos.setEmail(medicos.getEmail());
                }
                if (medicos.getEstado() != null) {
                    existingMedicos.setEstado(medicos.getEstado());
                }

                return existingMedicos;
            })
            .map(medicosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicos> findAll() {
        LOG.debug("Request to get all Medicos");
        return medicosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicos> findOne(Long id) {
        LOG.debug("Request to get Medicos : {}", id);
        return medicosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Medicos : {}", id);
        medicosRepository.deleteById(id);
    }
}
