package com.mycompany.myapp.domain.model;
import com.mycompany.myapp.domain.Asientos;
import com.mycompany.myapp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaModel {

    private Long id;

    private Long ventaIdCatedra;

    private LocalDate fechaVenta;

    private Double precioVenta;

    private Boolean resultado;

    private String descripcion;

    private Integer cantidadAsientos;

    private String estado;

    private Set<Asientos> asientos = new HashSet<>();

    private Long userId;

}
