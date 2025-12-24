package com.example.Proxy.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloquearAsientosDTO {
    private Boolean resultado;
    private String descripcion;
    private Integer eventoId;
    private List<AsientosCompletosDTO> asientos;
}
