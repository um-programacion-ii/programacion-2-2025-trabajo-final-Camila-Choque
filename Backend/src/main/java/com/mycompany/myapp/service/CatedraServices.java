package com.mycompany.myapp.service;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CatedraServices {
    private final ProxyClient proxyClient;

    public CatedraServices(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }
    public String registrar(String body) {
        return proxyClient.registrar(body);
    }
    public EventoDTO conseguirEventoPorId(String eventoId) {
        return proxyClient.conseguirEventoPorId(eventoId);
    }
    public List<EventoDTO> conseguirEventos() {
        return proxyClient.conseguirEventos();
    }
    public List<EventoResumidoDTO> conseguirEventosResumidos(){
        return proxyClient.conseguirEventosResumidos();
    }
    public BloquearAsientosDTO bloquearAsientos(BloquearAsientosDTO dto) {
        return proxyClient.bloquearAsientos(dto);
    }
    public AsientosRedisDTO getAsientos(Long eventoId) {
        return proxyClient.getAsientosEvento(eventoId);
    }

    public List<AsientosCompletoDTO> getAsientosNoDisponibles(Long eventoId) {
        return proxyClient.getAsientosNoDisponibles(eventoId);
    }

    public List<AsientosRedisDTO> getAsientosVendidos(Long eventoId) {
        return proxyClient.getAsientosVendidos(eventoId);
    }

    public List<AsientosRedisDTO> getAsientosBloqueados(Long eventoId) {
        return proxyClient.getAsientosBloqueados(eventoId);
    }

    public Map<String, Object> getEstadisticasEvento(Long eventoId) {
        return proxyClient.getEstadisticasEvento(eventoId);
    }

    public Set<String> getAllKeys() {
        return proxyClient.getAllKeys();
    }

    public Map<String, String> getAllKeysWithValues() {
        return proxyClient.getAllKeysWithValues();
    }
    public VentaAsientosResponse realizarVenta(VentaAsientosRequest request) {
        return proxyClient.realizarVenta(request);
    }
}


