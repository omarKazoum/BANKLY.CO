package com.example.gateway.configuration;

import com.example.gateway.entity.RoleEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebSecurity
@Data
public class SpringSecurityConfig {

    @Autowired
    ApplicationContext applicationContext;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .httpBasic(hb->hb.disable()).csrf().disable()
                .authorizeExchange().pathMatchers("/auth/**").permitAll()
                .pathMatchers("/wallet/**").hasRole(RoleEnum.CLIENT.toString());
        http.authenticationManager(applicationContext.getBean(ReactiveAuthenticationManager.class));
        http.securityContextRepository(applicationContext.getBean(ServerSecurityContextRepository.class));
        //http.addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
    @Bean
    ReactiveAuthenticationManager authenticationManager(final MapReactiveUserDetailsService mapReactiveUserDetailsService,PasswordEncoder passwordEncoder){
        return (authentication) -> {
                UserDetails userDetails=mapReactiveUserDetailsService.findByUsername(authentication.getName()).block();
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
                if(userDetails!=null) {
                    return Mono.just(token);
                }
                //return Mono.just(authentication);
                return Mono.empty();
            };
        };

    /*@Bean
    MyMapReactiveUserDetailsService MapReactiveUserDetailsService(){
        return new MyMapReactiveUserDetailsService();
    }*/
    @Bean
    ServerAuthenticationSuccessHandler webFilterChainServerAuthenticationSuccessHandler() {
        return new ServerAuthenticationSuccessHandler() {
            @Override
            public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                ServerWebExchange exchange = webFilterExchange.getExchange();
                return webFilterExchange.getChain().filter(exchange);
            }
        };
    }
}
