package com.mycompany.myapp.service;
import com.mycompany.myapp.service.client.ProxyClient;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.EventoResumidoDTO;
import org.springframework.stereotype.Service;
import java.util.List;

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

}
