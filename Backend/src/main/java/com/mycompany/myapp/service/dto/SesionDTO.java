package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Sesion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SesionDTO implements Serializable {

    private Long id;

    private String token;

    private String estadoFlujo;

    private Long eventoSeleccionado;

    private LocalDate ultimaActividad;

    private LocalDate expiraEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstadoFlujo() {
        return estadoFlujo;
    }

    public void setEstadoFlujo(String estadoFlujo) {
        this.estadoFlujo = estadoFlujo;
    }

    public Long getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    public void setEventoSeleccionado(Long eventoSeleccionado) {
        this.eventoSeleccionado = eventoSeleccionado;
    }

    public LocalDate getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(LocalDate ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public LocalDate getExpiraEn() {
        return expiraEn;
    }

    public void setExpiraEn(LocalDate expiraEn) {
        this.expiraEn = expiraEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SesionDTO)) {
            return false;
        }

        SesionDTO sesionDTO = (SesionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sesionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SesionDTO{" +
            "id=" + getId() +
            ", token='" + getToken() + "'" +
            ", estadoFlujo='" + getEstadoFlujo() + "'" +
            ", eventoSeleccionado=" + getEventoSeleccionado() +
            ", ultimaActividad='" + getUltimaActividad() + "'" +
            ", expiraEn='" + getExpiraEn() + "'" +
            "}";
    }
}
