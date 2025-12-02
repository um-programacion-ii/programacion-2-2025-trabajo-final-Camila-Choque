package com.example.Proxy.domain;
import com.example.Proxy.dto.RegistrarUsuarioDto;
import com.example.Proxy.dto.RegistrarUsuarioResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CatedraClient {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(CatedraClient.class);

    public CatedraClient(WebClient webClient) {
        this.webClient = webClient;
        // Log the WebClient's baseUrl when the client is constructed (if available)
        try {
            String base = webClient.mutate().build().toString();
            logger.info("CatedraClient constructed, WebClient.toString(): {}", base);
        } catch (Exception e) {
            logger.debug("No se pudo obtener representación del WebClient: {}", e.getMessage());
        }
    }

    public RegistrarUsuarioResponse registarUsuario(RegistrarUsuarioDto dto) {
        logger.info("Invocando endpoint POST /agregar_usuario usando WebClient");
        return webClient.post()
            .uri("/agregar_usuario")
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(RegistrarUsuarioResponse.class)
            .block();
    }

}