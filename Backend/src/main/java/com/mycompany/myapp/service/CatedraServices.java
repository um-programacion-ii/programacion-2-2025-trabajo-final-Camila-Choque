package com.mycompany.myapp.service;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.infrastructure.persistence.entity.Venta;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.repository.VentaRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CatedraServices {
    private final ProxyClient proxyClient;
    private final VentaRepository ventaRepository;
    private final UserRepository userRepository;

    public CatedraServices(ProxyClient proxyClient, VentaRepository ventaRepository, UserRepository userRepository) {
        this.proxyClient = proxyClient;
        this.ventaRepository = ventaRepository;
        this.userRepository = userRepository;
    }
    public String registrar(String body) {
        return proxyClient.registrar(body);
    }
    public EventoExternoDTO conseguirEventoPorId(String eventoId) {
        return proxyClient.conseguirEventoPorId(eventoId);
    }
    public List<EventoExternoDTO> conseguirEventos() {
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
        VentaAsientosResponse response = proxyClient.realizarVenta(request);
        if (Boolean.TRUE.equals(response.getResultado())) {
            Venta venta = new Venta();
            venta.setVentaIdCatedra(response.getVentaId());
            venta.setFechaVenta(response.getFechaVenta().toLocalDate());
            venta.setPrecioVenta(response.getPrecioVenta());
            venta.setResultado(response.getResultado());
            venta.setDescripcion(response.getDescripcion());
            venta.setCantidadAsientos(response.getAsientos().size());
            venta.setEstado("COMPLETADA");
            String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
            User user = userRepository.findOneByLogin(login).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            venta.setUser(user);
            ventaRepository.save(venta);
        }
        return response;

    }
}


