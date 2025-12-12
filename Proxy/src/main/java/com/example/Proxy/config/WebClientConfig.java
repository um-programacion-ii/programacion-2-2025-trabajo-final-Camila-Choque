package com.example.Proxy.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class WebClientConfig {

    @Value("${proxy-target.base-url}")
    private String baseUrl;

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public WebClient webClient( @Value("${proxy-target.base-url}") String base) {
        return WebClient.builder().baseUrl(base.replaceAll("/$", ""))
                .filter((request, next) -> {
                    logger.info("WebClient REQUEST -> {} {}", request.method(), request.url());
                    request.headers().forEach((k,v) -> logger.info("REQ header: {}={}", k, v));
                    return next.exchange(request)
                            .doOnNext(resp -> {
                                logger.info("WebClient RESPONSE status: {}", resp.statusCode());
                                resp.headers().asHttpHeaders().forEach((k,v) -> logger.info("RESP header: {}={}", k, v));
                            });
                })
                .build();
    }


}