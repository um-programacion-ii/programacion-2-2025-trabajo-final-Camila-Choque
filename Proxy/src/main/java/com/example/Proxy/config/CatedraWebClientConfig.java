package com.example.Proxy.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class CatedraWebClientConfig {

    @Value("${proxy-target.base-url}")
    private String baseUrl;

    private static final Logger logger = LoggerFactory.getLogger(CatedraWebClientConfig.class);

    @Bean(name = "catedraWebClient")
    public WebClient CatedraWebClient(WebClient.Builder builder) {
        logger.info("proxy-target.base-url={}", baseUrl);
        return builder
                .baseUrl(baseUrl)
                .build();
    }
}