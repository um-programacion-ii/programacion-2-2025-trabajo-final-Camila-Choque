package com.mycompany.myapp.infrastructure.web.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponse {
    private Long id;

    private Long ventaIdCatedra;

    private LocalDate fechaVenta;

    private Double precioVenta;

    private Boolean resultado;

    private String descripcion;

    private Integer cantidadAsientos;

    private String estado;

    private List<AsientoResponse> asientos;
}
