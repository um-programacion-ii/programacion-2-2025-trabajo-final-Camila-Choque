package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "venta_id_catedra")
    private Long ventaIdCatedra;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "resultado")
    private Boolean resultado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "cantidad_asientos")
    private Integer cantidadAsientos;

    @Column(name = "estado")
    private String estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVentaIdCatedra() {
        return this.ventaIdCatedra;
    }

    public Venta ventaIdCatedra(Long ventaIdCatedra) {
        this.setVentaIdCatedra(ventaIdCatedra);
        return this;
    }

    public void setVentaIdCatedra(Long ventaIdCatedra) {
        this.ventaIdCatedra = ventaIdCatedra;
    }

    public LocalDate getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(LocalDate fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getPrecioVenta() {
        return this.precioVenta;
    }

    public Venta precioVenta(Double precioVenta) {
        this.setPrecioVenta(precioVenta);
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Boolean getResultado() {
        return this.resultado;
    }

    public Venta resultado(Boolean resultado) {
        this.setResultado(resultado);
        return this;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Venta descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidadAsientos() {
        return this.cantidadAsientos;
    }

    public Venta cantidadAsientos(Integer cantidadAsientos) {
        this.setCantidadAsientos(cantidadAsientos);
        return this;
    }

    public void setCantidadAsientos(Integer cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }

    public String getEstado() {
        return this.estado;
    }

    public Venta estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return getId() != null && getId().equals(((Venta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", ventaIdCatedra=" + getVentaIdCatedra() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            ", resultado='" + getResultado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidadAsientos=" + getCantidadAsientos() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
