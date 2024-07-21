package com.sicredi.voting.integration.config;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class DefaultClientConfig {
    @Bean
    private RequestInterceptor interceptor() {
        return template -> {
            template.header("Accpet", "application/json");
            template.header("Content-Type", "application/json");
        };
    }

}
