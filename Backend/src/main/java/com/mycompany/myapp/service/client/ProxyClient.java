package com.mycompany.myapp.service.client;
import com.mycompany.myapp.service.dto.EventoDTO;
import com.mycompany.myapp.service.dto.EventoResumidoDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

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
}
