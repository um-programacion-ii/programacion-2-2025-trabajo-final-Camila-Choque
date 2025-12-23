package com.example.Proxy.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaAsientosRequest implements Serializable {
    private Long eventoId;
    private Instant fecha;
    private Double precioVenta;
    private List<AsientosVentasDTO> asientos;
}