package com.example.gateway_1;

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
                .route("productsRoute",p->p.path("/products/**")
                        .uri("http://localhost:9001/")).build();
    }
}
