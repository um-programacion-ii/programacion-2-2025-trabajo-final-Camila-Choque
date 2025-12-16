package com.example.Proxy.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendWebClientConfig {

    @Value("${backend.url}")
    private String backendUrl;

    @Bean(name = "backendWebClient")
    public WebClient backendWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(backendUrl)
                .build();
    }
}