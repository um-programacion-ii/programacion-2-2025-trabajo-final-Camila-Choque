package com.mycompany.myapp.service.client;
import com.mycompany.myapp.service.dto.AsientosRedisDTO;
import com.mycompany.myapp.service.dto.BloquearAsientosDTO;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.EventoResumidoDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

// Conecta el back con el proxy y el proxy se comunica con el servicio de la catedra
@Service
public class ProxyClient {
    private final WebClient proxyWebClient;

    public ProxyClient(WebClient proxyWebClient) {
        this.proxyWebClient = proxyWebClient;
    }

    public String registrar(String body) {
        return proxyWebClient.post()
                .uri("/proxy/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public EventoDTO conseguirEventoPorId(String eventoId) {
        return proxyWebClient.get()
                .uri("/proxy/eventos/{id}", eventoId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(EventoDTO.class)
                .block();
    }
    public List<EventoResumidoDTO> conseguirEventosResumidos() {
        return proxyWebClient.get()
                .uri("/proxy/eventos-resumidos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EventoResumidoDTO.class)
                .collectList()
                .block();
    }


    public List<EventoDTO> conseguirEventos() {
        return proxyWebClient.get()
                .uri("/proxy/eventos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EventoDTO.class)
                .collectList()
                .block();
    }
    public BloquearAsientosDTO bloquearAsientos( BloquearAsientosDTO dto) {
        return proxyWebClient.post()
                .uri("/proxy/bloquear-asientos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(BloquearAsientosDTO.class)
                .block();
    }
    public AsientosRedisDTO getAsientosEvento(Long eventoId) {
        return proxyWebClient.get()
            .uri("/proxy/asientos/evento/{eventoId}", eventoId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(AsientosRedisDTO.class)
            .block();
    }

    public List<String> getAsientosNoDisponibles(Long eventoId){
        return proxyWebClient.get()
            .uri("/proxy/asientos/evento/{eventoId}/no-disponibles", eventoId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(String.class)
            .collectList()
            .block();
    }

    public List<AsientosRedisDTO> getAsientosVendidos(Long eventoId){
        return proxyWebClient.get()
            .uri("/proxy/asientos/evento/{eventoId}/vendidos", eventoId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(AsientosRedisDTO.class)
            .collectList()
            .block();
    }

    public List<AsientosRedisDTO> getAsientosBloqueados(Long eventoId){
        return proxyWebClient.get()
            .uri("/proxy/asientos/evento/{eventoId}/bloqueados", eventoId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(AsientosRedisDTO.class)
            .collectList()
            .block();
    }

    public Map<String, Object> getEstadisticasEvento(Long eventoId){
        return proxyWebClient.get()
            .uri("/proxy/asientos/evento/{eventoId}/estadisticas", eventoId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();
    }
    public Set<String> getAllKeys() {
        return new HashSet<>(Objects.requireNonNull(proxyWebClient.get()
            .uri("/proxy/asientos/debug/keys")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(String.class)
            .collectList()
            .block()));
    }

    public Map<String, String> getAllKeysWithValues(){
        return proxyWebClient.get()
            .uri("/proxy/asientos/debug/all")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
            .block();
    }
}
