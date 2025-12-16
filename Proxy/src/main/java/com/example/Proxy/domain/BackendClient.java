package com.example.Proxy.domain;


import com.example.Proxy.config.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Component
@Slf4j
public class BackendClient {
    @Autowired
    @Qualifier("backendWebClient")
    private  WebClient backendClient;
    @Autowired
    private JwtUtils jwtUtils;

    private static final int MAX_REINTENTOS = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REINTENTO_DELAY = Duration.ofSeconds(2);

    public BackendClient(
            @Value("${backend.url}") String backendUrl,
            WebClient.Builder builder
    ) {
        this.backendClient = builder
                .baseUrl(backendUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Envia la notificación HTTP al backend
     */
    public Mono<Map<String, Object>> notificarCambioEvento(Map<String, Object> body) {
        String backendToken = jwtUtils.generateToken("proxy-service");
        log.info("Token generado: {}", backendToken);
        return backendClient.post()
                .uri("/api/internal/notificacion/eventos/cambios")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + backendToken)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .timeout(TIMEOUT)
                .retryWhen(Retry.backoff(MAX_REINTENTOS, REINTENTO_DELAY)
                        .filter(this::esErrorRecuperable)
                        .doBeforeRetry(retrySignal ->
                                log.warn("Reintento {}/{} después de error: {}",
                                        retrySignal.totalRetries() + 1,
                                        MAX_REINTENTOS,
                                        retrySignal.failure().getMessage())
                        )
                )
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("Error HTTP {}: {}",
                            ex.getStatusCode(), ex.getResponseBodyAsString());
                    return Mono.error(ex);
                });
    }

    private boolean esErrorRecuperable(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex) {
            HttpStatus status = (HttpStatus) ex.getStatusCode();

            if (status.is4xxClientError())
                return status == HttpStatus.REQUEST_TIMEOUT ||
                        status == HttpStatus.TOO_MANY_REQUESTS;

            return status.is5xxServerError();
        }
        return true;
    }
}