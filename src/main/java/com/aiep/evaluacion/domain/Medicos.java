package com.aiep.evaluacion.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Medicos.
 */
@Entity
@Table(name = "medicos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "rut")
    private String rut;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "email")
    private String email;

    @Column(name = "estado")
    private Boolean estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Medicos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Medicos nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return this.rut;
    }

    public Medicos rut(String rut) {
        this.setRut(rut);
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public Medicos especialidad(String especialidad) {
        this.setEspecialidad(especialidad);
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Medicos telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Medicos ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmail() {
        return this.email;
    }

    public Medicos email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public Medicos estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medicos)) {
            return false;
        }
        return getId() != null && getId().equals(((Medicos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medicos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", rut='" + getRut() + "'" +
            ", especialidad='" + getEspecialidad() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", email='" + getEmail() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
