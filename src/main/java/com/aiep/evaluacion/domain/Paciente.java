package com.aiep.evaluacion.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paciente implements Serializable {

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

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Paciente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return this.rut;
    }

    public Paciente rut(String rut) {
        this.setRut(rut);
        return this;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public Paciente edad(Integer edad) {
        this.setEdad(edad);
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Paciente sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Paciente direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Paciente ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Paciente telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public Paciente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return getId() != null && getId().equals(((Paciente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", rut='" + getRut() + "'" +
            ", edad=" + getEdad() +
            ", sexo='" + getSexo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
