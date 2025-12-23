package com.example.Proxy.domain;
import com.example.Proxy.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class CatedraClient {

    @Qualifier("catedraWebClient")
    private final WebClient catedraWebClient;

    private static final Logger logger = LoggerFactory.getLogger(CatedraClient.class);

    private final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXJpLmNob3F1ZSIsImV4cCI6MTc2OTExNjYwOCwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTc2NjUyNDYwOH0.zN1Ec0jLCFJwOJEdP8v3zHONLKRn47LKrO94gLVCe1MQZ7ZqI61oXczaGEKXvtxTdVd6rc2Wxj1y_y1_lanPkQ";
    public CatedraClient(WebClient catedraWebClient) {
        this.catedraWebClient = catedraWebClient;

        try {
            String base = catedraWebClient.mutate().build().toString();
            logger.info("CatedraClient constructed, WebClient.toString(): {}", base);
        } catch (Exception e) {
            logger.debug("No se pudo obtener representación del WebClient: {}", e.getMessage());
        }
    }

    public RegistrarUsuarioResponse registarUsuario(RegistrarUsuarioDto dto) {
        logger.info("Invocando endpoint POST /agregar_usuario usando WebClient");
        return catedraWebClient.post()
            .uri("/v1/agregar_usuario")
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(RegistrarUsuarioResponse.class)
            .block();
    }

    public String login(LoginDto dto) {
        logger.info("Invocando endpoint POST /agregar_usuario usando WebClient");
        return catedraWebClient.post()
                .uri("/autenticate")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<EventoResumidoDto> conseguirEventosResumidos() {
        logger.info("Invocando endpoint GET /eventos_resumidos usando WebClient");
        return catedraWebClient.get()
                .uri("/endpoints/v1/eventos-resumidos")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EventoResumidoDto>>() {})
                .block();
    }
    //IA
    public List<EventoDto> conseguirEventos() {
        logger.info("Invocando endpoint GET /eventos usando WebClient");
        logger.info("Llamando a: {}/endpoints/v1/eventos", catedraWebClient);

        try {
            return catedraWebClient.get()
                    .uri("/endpoints/v1/eventos")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(body -> {
                                        logger.error(
                                                "Error desde servicio cátedra. Status: {} - Body: {}",
                                                response.statusCode(),
                                                body
                                        );
                                        return Mono.error(
                                                new RuntimeException("Error al obtener eventos desde la cátedra")
                                        );
                                    })
                    )
                    .bodyToFlux(EventoDto.class)
                    .collectList()
                    .doOnNext(eventos ->
                            logger.info("Eventos recibidos desde cátedra: {}", eventos.size())
                    )
                    .block();
        } catch (Exception e) {
            logger.error("Excepción llamando al servicio de cátedra", e);
            return List.of(); // fallback seguro
        }
    }


    public EventoDto conseguirEventosPorId(Long id) {
        logger.info("Invocando endpoint GET /eventos/{id} usando WebClient");
        return catedraWebClient.get()
                .uri("/endpoints/v1/evento/{id}", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(EventoDto.class)
                .block();
    }
    public BloquearAsientosDTO bloquearAsientos (BloquearAsientosRequest request){
        logger.info("Invocando endpoint POST /bloquear-asientos usando WebClient");
        return catedraWebClient.post()
                .uri("/endpoints/v1/bloquear-asientos")
                .header("Authorization", "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BloquearAsientosDTO.class)
                .block();
    }
    public VentaAsientosResponse realizarVenta(VentaAsientosRequest request) {
        logger.info("Invocando endpoint POST /realizar-venta usando WebClient");
        return catedraWebClient.post()
                .uri("/endpoints/v1/realizar-venta")
                .header("Authorization", "Bearer " + token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(VentaAsientosResponse.class)
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