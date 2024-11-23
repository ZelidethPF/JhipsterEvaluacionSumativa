package com.aiep.evaluacion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Agendamiento.
 */
@Entity
@Table(name = "agendamiento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agendamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "paciente")
    private String paciente;

    @Column(name = "medico")
    private String medico;

    @Column(name = "centro")
    private String centro;

    @Column(name = "fechahora")
    private ZonedDateTime fechahora;

    @Column(name = "estado")
    private String estado;

    @Column(name = "origen")
    private String origen;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fechaingreso")
    private LocalDate fechaingreso;

    @Column(name = "nivelprioridad")
    private String nivelprioridad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agendamiento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaciente() {
        return this.paciente;
    }

    public Agendamiento paciente(String paciente) {
        this.setPaciente(paciente);
        return this;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getMedico() {
        return this.medico;
    }

    public Agendamiento medico(String medico) {
        this.setMedico(medico);
        return this;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getCentro() {
        return this.centro;
    }

    public Agendamiento centro(String centro) {
        this.setCentro(centro);
        return this;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public ZonedDateTime getFechahora() {
        return this.fechahora;
    }

    public Agendamiento fechahora(ZonedDateTime fechahora) {
        this.setFechahora(fechahora);
        return this;
    }

    public void setFechahora(ZonedDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public String getEstado() {
        return this.estado;
    }

    public Agendamiento estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrigen() {
        return this.origen;
    }

    public Agendamiento origen(String origen) {
        this.setOrigen(origen);
        return this;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public Agendamiento motivo(String motivo) {
        this.setMotivo(motivo);
        return this;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaingreso() {
        return this.fechaingreso;
    }

    public Agendamiento fechaingreso(LocalDate fechaingreso) {
        this.setFechaingreso(fechaingreso);
        return this;
    }

    public void setFechaingreso(LocalDate fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getNivelprioridad() {
        return this.nivelprioridad;
    }

    public Agendamiento nivelprioridad(String nivelprioridad) {
        this.setNivelprioridad(nivelprioridad);
        return this;
    }

    public void setNivelprioridad(String nivelprioridad) {
        this.nivelprioridad = nivelprioridad;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agendamiento)) {
            return false;
        }
        return getId() != null && getId().equals(((Agendamiento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agendamiento{" +
            "id=" + getId() +
            ", paciente='" + getPaciente() + "'" +
            ", medico='" + getMedico() + "'" +
            ", centro='" + getCentro() + "'" +
            ", fechahora='" + getFechahora() + "'" +
            ", estado='" + getEstado() + "'" +
            ", origen='" + getOrigen() + "'" +
            ", motivo='" + getMotivo() + "'" +
            ", fechaingreso='" + getFechaingreso() + "'" +
            ", nivelprioridad='" + getNivelprioridad() + "'" +
            "}";
    }
}
