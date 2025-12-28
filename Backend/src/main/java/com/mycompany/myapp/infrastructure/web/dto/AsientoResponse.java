package com.mycompany.myapp.infrastructure.web.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsientoResponse {
    private int fila;
    private int columna;
}
