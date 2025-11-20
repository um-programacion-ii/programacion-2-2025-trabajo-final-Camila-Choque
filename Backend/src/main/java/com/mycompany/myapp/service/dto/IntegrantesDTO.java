package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Integrantes} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntegrantesDTO implements Serializable {

    private Long id;

    private String nombre;

    private String apellido;

    private String identificacion;

    private EventoDTO evento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrantesDTO)) {
            return false;
        }

        IntegrantesDTO integrantesDTO = (IntegrantesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integrantesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrantesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", evento=" + getEvento() +
            "}";
    }
}
