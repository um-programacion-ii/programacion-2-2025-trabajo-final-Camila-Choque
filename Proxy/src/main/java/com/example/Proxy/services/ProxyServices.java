package com.example.Proxy.services;
import com.example.Proxy.domain.CatedraClient;
import com.example.Proxy.dto.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProxyServices {

    private final CatedraClient catedraClient;

    public ProxyServices(CatedraClient catedraClient) {
        this.catedraClient = catedraClient;
    }

    public RegistrarUsuarioResponse registarUsuario(RegistrarUsuarioDto dto) {
        return catedraClient.registarUsuario(dto);
    }
    public String login(LoginDto dto) {
        return catedraClient.login(dto);
    }

    public List<EventoResumidoDto> conseguirEventosResumidos() {
        return catedraClient.conseguirEventosResumidos();
    }

    public List<EventoDto> conseguirEventos() {
        return catedraClient.conseguirEventos();
    }

    public EventoDto conseguirEventosPorId(Long id) {
        return catedraClient.conseguirEventosPorId(id);
    }

    public BloquearAsientosDTO bloquearAsientos(BloquearAsientosRequest dto) {
        return catedraClient.bloquearAsientos(dto);
    }
}