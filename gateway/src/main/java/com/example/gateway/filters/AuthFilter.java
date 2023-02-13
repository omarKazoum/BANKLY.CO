package com.example.gateway.filters;

import com.example.gateway.configuration.security.JwtUtil;
import com.example.gateway.service.implementations.MyMapReactiveUserDetailsService;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthFilter implements GlobalFilter {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MyMapReactiveUserDetailsService userDetailsService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestedEndpoint=exchange.getRequest().getURI().getPath();
        //if the request is for authentication endpoints then let it go
        if(requestedEndpoint.startsWith("/auth"))
            return chain.filter(exchange);

        String authorisationHeader=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(authorisationHeader!=null &&!authorisationHeader.isEmpty() && authorisationHeader.startsWith("Bearer ")){
            String jwt=authorisationHeader.substring(7);
            if(jwt==null || jwt.isEmpty()){
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return Mono.empty();
            }else {
                try{
                    String email=jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails=userDetailsService.findByUsername(email).block();
                    if(userDetails==null)
                    {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return Mono.empty();
                    }
                    else {
                        long userId=this.userDetailsService.getUserByEmail(email).getId();
                        return chain.filter(exchange.mutate().request(exchange.getRequest().mutate().headers(headers->headers.add("userId",""+userId)).build()).build());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return Mono.empty();                }
            }
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.empty();        }
}
