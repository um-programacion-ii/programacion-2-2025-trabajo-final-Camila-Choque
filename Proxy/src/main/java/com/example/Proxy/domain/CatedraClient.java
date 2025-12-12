package com.example.Proxy.domain;
import com.example.Proxy.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Component
public class CatedraClient {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(CatedraClient.class);
    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtLmNob3F1ZSIsImV4cCI6MTc2Nzk5OTYyMCwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTc2NTQwNzYyMH0.5gWtqudeKhTKJbjFKc1fRJdLm3hZl58Y2WcaHjhBVMPOlxhPsRUeUnCmdt2LYSpcEzep9b42uB5WgTga3aqc8Q";
    public CatedraClient(WebClient webClient) {
        this.webClient = webClient;

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
            .uri("/v1/agregar_usuario")
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(RegistrarUsuarioResponse.class)
            .block();
    }

    public String login(LoginDto dto) {
        logger.info("Invocando endpoint POST /agregar_usuario usando WebClient");
        return webClient.post()
                .uri("/autenticate")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<EventoResumidoDto> conseguirEventosResumidos() {
        logger.info("Invocando endpoint GET /eventos_resumidos usando WebClient");
        return webClient.get()
                .uri("/endpoints/v1/eventos-resumidos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EventoResumidoDto>>() {})
                .block();
    }

    public List<EventoDto> conseguirEventos() {
        logger.info("Invocando endpoint GET /eventos usando WebClient");
        logger.info("Llamando a: {}/eventos", webClient.toString());

        return webClient.get()
                .uri("/endpoints/v1/eventos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EventoDto>>() {})
                .block();
    }

    public EventoDto conseguirEventosPorId(Long id) {
        logger.info("Invocando endpoint GET /eventos/{id} usando WebClient");
        return webClient.get()
                .uri("/endpoints/v1/evento/{id}", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(EventoDto.class)
                .block();
    }
    /*
    public String conseguirEventos() {
        logger.info("Invocando endpoint GET /eventos (raw) usando WebClient");
        String body = webClient.get()
                .uri("/endpoints/v1/eventos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        logger.info("RAW response body: {}", body);
        return body;
    }
    */


}