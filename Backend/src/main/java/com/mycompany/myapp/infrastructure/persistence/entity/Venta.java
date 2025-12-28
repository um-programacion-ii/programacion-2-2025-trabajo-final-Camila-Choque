package com.mycompany.myapp.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta")
    @JsonIgnoreProperties(value = { "evento", "venta", "sesion" }, allowSetters = true)
    private Set<Asientos> asientos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = { "authorities" }, allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here


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
