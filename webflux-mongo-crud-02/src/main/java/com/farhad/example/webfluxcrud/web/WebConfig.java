package com.farhad.example.webfluxcrud.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.service.ProfileHandler;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    
    @Bean
    @org.springframework.context.annotation.Profile("default")
    public RouterFunction<ServerResponse> routeProfile(ProfileHandler profileHandler) {
        return
            route(GET("/api/profiles/{id}"), profileHandler::byId)
            .andRoute(DELETE("/api/profiles/{id}"), profileHandler::deleteById)
            .andRoute(GET("/api/profiles"), profileHandler::all)
            .andRoute(POST("/api/profiles"), profileHandler::create)
            .andRoute(PUT("/api/profiles/{id}"), profileHandler::updateById)
            ;
    }
}
