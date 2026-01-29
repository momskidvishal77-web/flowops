package com.flowops.gateway.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 1️ Allow auth endpoints without JWT
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // 2️ Read Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 3️ Extract token
        String token = authHeader.substring(7);

        try {
            // 4️ Validate token
            Claims claims = jwtUtil.validateToken(token);

            // 5️ Extract user identity (email stored as subject)
            String userEmail = claims.getSubject();

            // 6️ Inject header for downstream services
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(
                        exchange.getRequest().mutate()
                            .header("X-User-Email", userEmail)
                            .build()
                    )
                    .build();

            // 7️ Continue request chain
            return chain.filter(mutatedExchange);

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}
