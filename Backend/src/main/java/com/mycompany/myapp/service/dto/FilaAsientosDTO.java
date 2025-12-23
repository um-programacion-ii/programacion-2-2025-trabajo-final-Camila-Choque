package com.mycompany.myapp.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FilaAsientosDTO {
    private int fila;
    private List<AsientoEstadoDTO> asientos;

    @Data
    @AllArgsConstructor
    public static class AsientoEstadoDTO {
        private int columna;
        private String estado;
    }
}


