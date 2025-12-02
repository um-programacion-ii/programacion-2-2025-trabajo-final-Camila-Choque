package com.example.Proxy.dto;
import lombok.Data;

@Data
public class RegistrarUsuarioResponse {

    private boolean creado;
    private String resultado;
    private String token;
}