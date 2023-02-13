package com.example.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SpringCloudConfig {

    @Bean
    RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder
                .routes()
                .route("operationsRoutes",p->p.path("/operations/**")
                        .uri("http://localhost:9106/"))
                .route("walletRoutes",p->p.path("/wallet/**").uri("http://localhost:9105/"))
                .build();
    }
}
