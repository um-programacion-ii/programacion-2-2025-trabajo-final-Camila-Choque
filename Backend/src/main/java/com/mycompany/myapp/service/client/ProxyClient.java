package com.mycompany.myapp.service.client;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
}
