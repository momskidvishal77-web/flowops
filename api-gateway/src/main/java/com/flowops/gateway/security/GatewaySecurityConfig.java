package com.flowops.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http
            //  Disable default security features
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

            //  Authorization rules
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(
                        "/auth/login",
                        "/auth/register",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
                .anyExchange().authenticated()
            );

        return http.build();
    }
}
