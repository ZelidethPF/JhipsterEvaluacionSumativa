package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Agendamiento;
import com.aiep.evaluacion.repository.AgendamientoRepository;
import com.aiep.evaluacion.service.AgendamientoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Agendamiento}.
 */
@Service
@Transactional
public class AgendamientoServiceImpl implements AgendamientoService {

    private static final Logger LOG = LoggerFactory.getLogger(AgendamientoServiceImpl.class);

    private final AgendamientoRepository agendamientoRepository;

    public AgendamientoServiceImpl(AgendamientoRepository agendamientoRepository) {
        this.agendamientoRepository = agendamientoRepository;
    }

    @Override
    public Agendamiento save(Agendamiento agendamiento) {
        LOG.debug("Request to save Agendamiento : {}", agendamiento);
        return agendamientoRepository.save(agendamiento);
    }

    @Override
    public Agendamiento update(Agendamiento agendamiento) {
        LOG.debug("Request to update Agendamiento : {}", agendamiento);
        return agendamientoRepository.save(agendamiento);
    }

    @Override
    public Optional<Agendamiento> partialUpdate(Agendamiento agendamiento) {
        LOG.debug("Request to partially update Agendamiento : {}", agendamiento);

        return agendamientoRepository
            .findById(agendamiento.getId())
            .map(existingAgendamiento -> {
                if (agendamiento.getPaciente() != null) {
                    existingAgendamiento.setPaciente(agendamiento.getPaciente());
                }
                if (agendamiento.getMedico() != null) {
                    existingAgendamiento.setMedico(agendamiento.getMedico());
                }
                if (agendamiento.getCentro() != null) {
                    existingAgendamiento.setCentro(agendamiento.getCentro());
                }
                if (agendamiento.getFechahora() != null) {
                    existingAgendamiento.setFechahora(agendamiento.getFechahora());
                }
                if (agendamiento.getEstado() != null) {
                    existingAgendamiento.setEstado(agendamiento.getEstado());
                }
                if (agendamiento.getOrigen() != null) {
                    existingAgendamiento.setOrigen(agendamiento.getOrigen());
                }
                if (agendamiento.getMotivo() != null) {
                    existingAgendamiento.setMotivo(agendamiento.getMotivo());
                }
                if (agendamiento.getFechaingreso() != null) {
                    existingAgendamiento.setFechaingreso(agendamiento.getFechaingreso());
                }
                if (agendamiento.getNivelprioridad() != null) {
                    existingAgendamiento.setNivelprioridad(agendamiento.getNivelprioridad());
                }

                return existingAgendamiento;
            })
            .map(agendamientoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agendamiento> findAll() {
        LOG.debug("Request to get all Agendamientos");
        return agendamientoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agendamiento> findOne(Long id) {
        LOG.debug("Request to get Agendamiento : {}", id);
        return agendamientoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Agendamiento : {}", id);
        agendamientoRepository.deleteById(id);
    }
}
