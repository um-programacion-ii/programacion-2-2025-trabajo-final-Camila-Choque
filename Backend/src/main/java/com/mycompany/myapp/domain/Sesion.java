package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Sesion.
 */
@Entity
@Table(name = "sesion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "estado_flujo")
    private String estadoFlujo;

    @Column(name = "evento_seleccionado")
    private Long eventoSeleccionado;

    @Column(name = "ultima_actividad")
    private LocalDate ultimaActividad;

    @Column(name = "expira_en")
    private LocalDate expiraEn;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sesion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public Sesion token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstadoFlujo() {
        return this.estadoFlujo;
    }

    public Sesion estadoFlujo(String estadoFlujo) {
        this.setEstadoFlujo(estadoFlujo);
        return this;
    }

    public void setEstadoFlujo(String estadoFlujo) {
        this.estadoFlujo = estadoFlujo;
    }

    public Long getEventoSeleccionado() {
        return this.eventoSeleccionado;
    }

    public Sesion eventoSeleccionado(Long eventoSeleccionado) {
        this.setEventoSeleccionado(eventoSeleccionado);
        return this;
    }

    public void setEventoSeleccionado(Long eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public LocalDate getUltimaActividad() {
        return this.ultimaActividad;
    }

    public Sesion ultimaActividad(LocalDate ultimaActividad) {
        this.setUltimaActividad(ultimaActividad);
        return this;
    }

    public void setUltimaActividad(LocalDate ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public LocalDate getExpiraEn() {
        return this.expiraEn;
    }

    public Sesion expiraEn(LocalDate expiraEn) {
        this.setExpiraEn(expiraEn);
        return this;
    }

    public void setExpiraEn(LocalDate expiraEn) {
        this.expiraEn = expiraEn;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sesion)) {
            return false;
        }
        return getId() != null && getId().equals(((Sesion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sesion{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", estadoFlujo='" + getEstadoFlujo() + "'" +
            ", eventoSeleccionado=" + getEventoSeleccionado() +
            ", ultimaActividad='" + getUltimaActividad() + "'" +
            ", expiraEn='" + getExpiraEn() + "'" +
            "}";
    }
}
