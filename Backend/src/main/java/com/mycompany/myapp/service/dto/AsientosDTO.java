package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Asientos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AsientosDTO implements Serializable {

    private Long id;

    private Integer fila;

    private Integer columna;

    private String persona;

    private String estado;

    private EventoDTO evento;

    private VentaDTO venta;

    private SesionDTO sesion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public VentaDTO getVenta() {
        return venta;
    }

    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    public SesionDTO getSesion() {
        return sesion;
    }

    public void setSesion(SesionDTO sesion) {
        this.sesion = sesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsientosDTO)) {
            return false;
        }

        AsientosDTO asientosDTO = (AsientosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, asientosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsientosDTO{" +
            "id=" + getId() +
            ", fila=" + getFila() +
            ", columna=" + getColumna() +
            ", persona='" + getPersona() + "'" +
            ", estado='" + getEstado() + "'" +
            ", evento=" + getEvento() +
            ", venta=" + getVenta() +
            ", sesion=" + getSesion() +
            "}";
    }
}
