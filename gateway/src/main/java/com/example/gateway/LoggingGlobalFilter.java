package com.example.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {
    Logger logger= LoggerFactory.getILoggerFactory().getLogger(LoggingGlobalFilter.class.getName());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path=exchange.getRequest().getPath().toString();
        logger.info("got a request with path "+path);
        ServerWebExchange modifiedExchange=exchange.mutate().request(r->r.path(exchange.getRequest().getPath().toString().replace("/products",""))).build();
        return chain.filter(modifiedExchange).then(Mono.fromRunnable(()->{
            logger.info("request post filter");
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
