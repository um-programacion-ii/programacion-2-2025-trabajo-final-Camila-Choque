package com.mycompany.myapp.service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrantesExternosDTO {
    private String nombre;
    private String apellido;
    private String identificacion;
}
