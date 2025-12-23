package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsientosVentasDTO {
    private Integer fila;
    private Integer columna;
    private String persona;
    private String estado;
}
