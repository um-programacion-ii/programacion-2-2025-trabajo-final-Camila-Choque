package com.mycompany.myapp.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class MapaAsientosDTO {
    private Long eventoId;
    private int filas;
    private int columnas;
    private List<FilaAsientosDTO> asientos;
}
