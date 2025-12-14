package com.mycompany.myapp.config;
import com.mycompany.myapp.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;

@Configuration
@Slf4j
public class ProxyClientConfig {

    private final JwtUtils jwtUtils;

    @Value("${proxy.base-url}")
    private String proxyBaseUrl; // Direccion del Proxy

    public ProxyClientConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public WebClient proxyWebClient() {
        return WebClient.builder()
            .baseUrl(proxyBaseUrl)
            .filter((request, next) -> {
                String token = jwtUtils.generateServiceToken();

                log.debug("JWT enviado al proxy: {}", token);

                ClientRequest newRequest = ClientRequest.from(request)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();

                return next.exchange(newRequest);
            })
            .build();
    }
}
