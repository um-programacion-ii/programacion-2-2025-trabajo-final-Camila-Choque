package com.example.Proxy.dto;
import lombok.Data;

@Data
public class RegistrarUsuarioDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String nombreAlumno;
    private String descripcionProyecto;
}
