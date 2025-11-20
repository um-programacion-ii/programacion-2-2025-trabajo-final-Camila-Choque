package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Asientos.
 */
@Entity
@Table(name = "asientos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asientos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fila")
    private Integer fila;

    @Column(name = "columna")
    private Integer columna;

    @Column(name = "persona")
    private String persona;

    @Column(name = "estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "asientos" }, allowSetters = true)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "asientos" }, allowSetters = true)
    private Sesion sesion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asientos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFila() {
        return this.fila;
    }

    public Asientos fila(Integer fila) {
        this.setFila(fila);
        return this;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return this.columna;
    }

    public Asientos columna(Integer columna) {
        this.setColumna(columna);
        return this;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public String getPersona() {
        return this.persona;
    }

    public Asientos persona(String persona) {
        this.setPersona(persona);
        return this;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getEstado() {
        return this.estado;
    }

    public Asientos estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Asientos venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public Sesion getSesion() {
        return this.sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public Asientos sesion(Sesion sesion) {
        this.setSesion(sesion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asientos)) {
            return false;
        }
        return getId() != null && getId().equals(((Asientos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asientos{" +
            "id=" + getId() +
            ", fila=" + getFila() +
            ", columna=" + getColumna() +
            ", persona='" + getPersona() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
