package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "resumen")
    private String resumen;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "fila_asientos")
    private Integer filaAsientos;

    @Column(name = "columna_asientos")
    private Integer columnaAsientos;

    @Column(name = "precio_entrada")
    private Double precioEntrada;

    @Column(name = "evento_tipo_nombre")
    private String eventoTipoNombre;

    @Column(name = "evento_tipo_descripcion")
    private String eventoTipoDescripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "ultima_actualizacion")
    private LocalDate ultimaActualizacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "evento")
    @JsonIgnoreProperties(value = { "evento" }, allowSetters = true)
    private Set<Integrantes> integrantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Evento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Evento titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return this.resumen;
    }

    public Evento resumen(String resumen) {
        this.setResumen(resumen);
        return this;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Evento descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Evento fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Evento direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return this.imagen;
    }

    public Evento imagen(String imagen) {
        this.setImagen(imagen);
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getFilaAsientos() {
        return this.filaAsientos;
    }

    public Evento filaAsientos(Integer filaAsientos) {
        this.setFilaAsientos(filaAsientos);
        return this;
    }

    public void setFilaAsientos(Integer filaAsientos) {
        this.filaAsientos = filaAsientos;
    }

    public Integer getColumnaAsientos() {
        return this.columnaAsientos;
    }

    public Evento columnaAsientos(Integer columnaAsientos) {
        this.setColumnaAsientos(columnaAsientos);
        return this;
    }

    public void setColumnaAsientos(Integer columnaAsientos) {
        this.columnaAsientos = columnaAsientos;
    }

    public Double getPrecioEntrada() {
        return this.precioEntrada;
    }

    public Evento precioEntrada(Double precioEntrada) {
        this.setPrecioEntrada(precioEntrada);
        return this;
    }

    public void setPrecioEntrada(Double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public String getEventoTipoNombre() {
        return this.eventoTipoNombre;
    }

    public Evento eventoTipoNombre(String eventoTipoNombre) {
        this.setEventoTipoNombre(eventoTipoNombre);
        return this;
    }

    public void setEventoTipoNombre(String eventoTipoNombre) {
        this.eventoTipoNombre = eventoTipoNombre;
    }

    public String getEventoTipoDescripcion() {
        return this.eventoTipoDescripcion;
    }

    public Evento eventoTipoDescripcion(String eventoTipoDescripcion) {
        this.setEventoTipoDescripcion(eventoTipoDescripcion);
        return this;
    }

    public void setEventoTipoDescripcion(String eventoTipoDescripcion) {
        this.eventoTipoDescripcion = eventoTipoDescripcion;
    }

    public String getEstado() {
        return this.estado;
    }

    public Evento estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getUltimaActualizacion() {
        return this.ultimaActualizacion;
    }

    public Evento ultimaActualizacion(LocalDate ultimaActualizacion) {
        this.setUltimaActualizacion(ultimaActualizacion);
        return this;
    }

    public void setUltimaActualizacion(LocalDate ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Set<Integrantes> getIntegrantes() {
        return this.integrantes;
    }

    public void setIntegrantes(Set<Integrantes> integrantes) {
        if (this.integrantes != null) {
            this.integrantes.forEach(i -> i.setEvento(null));
        }
        if (integrantes != null) {
            integrantes.forEach(i -> i.setEvento(this));
        }
        this.integrantes = integrantes;
    }

    public Evento integrantes(Set<Integrantes> integrantes) {
        this.setIntegrantes(integrantes);
        return this;
    }

    public Evento addIntegrantes(Integrantes integrantes) {
        this.integrantes.add(integrantes);
        integrantes.setEvento(this);
        return this;
    }

    public Evento removeIntegrantes(Integrantes integrantes) {
        this.integrantes.remove(integrantes);
        integrantes.setEvento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return getId() != null && getId().equals(((Evento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", resumen='" + getResumen() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", filaAsientos=" + getFilaAsientos() +
            ", columnaAsientos=" + getColumnaAsientos() +
            ", precioEntrada=" + getPrecioEntrada() +
            ", eventoTipoNombre='" + getEventoTipoNombre() + "'" +
            ", eventoTipoDescripcion='" + getEventoTipoDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", ultimaActualizacion='" + getUltimaActualizacion() + "'" +
            "}";
    }
}
