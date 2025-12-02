package com.example.Proxy.services;
import com.example.Proxy.domain.CatedraClient;
import com.example.Proxy.dto.RegistrarUsuarioDto;
import com.example.Proxy.dto.RegistrarUsuarioResponse;
import org.springframework.stereotype.Service;

@Service
public class ProxyServices {

    private final CatedraClient catedraClient;

    public ProxyServices(CatedraClient catedraClient) {
        this.catedraClient = catedraClient;
    }

    public RegistrarUsuarioResponse registarUsuario(RegistrarUsuarioDto dto) {
        return catedraClient.registarUsuario(dto);
    }
}