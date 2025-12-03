package com.mycompany.myapp.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class ProxyClientConfig {
    @Bean
    public WebClient proxyWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080") //direccion del proxy
                .build();
    }
}
