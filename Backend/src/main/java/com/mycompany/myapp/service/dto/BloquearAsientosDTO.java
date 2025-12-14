package com.mycompany.myapp.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloquearAsientosDTO {
    private Boolean resultados;
    private String descripcion;
    private Integer eventoId;
    private List<AsientosCompletoDTO> asientos;
}
