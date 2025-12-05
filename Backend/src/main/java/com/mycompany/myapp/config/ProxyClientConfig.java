package com.mycompany.myapp.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;

@Configuration
public class ProxyClientConfig {
    @Value("${proxy.base-url}")
    private String proxyHost;
    @Bean
    public WebClient proxyWebClient() {
        return WebClient.builder()
                .baseUrl(proxyHost) //direccion del proxy
                .build();
    }
}
