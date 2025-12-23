package com.example.Proxy.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaAsientosResponse {
    private Long eventoId;
    private Long ventaId;
    private LocalDateTime fechaVenta;
    private List<AsientosVentasDTO> asientos;
    private Boolean resultado;
    private String descripcion;
    private Double precioVenta;
}
