package com.farhad.example.webfluxcrud.web;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.rest.PostHandler;


@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> postRoutes(PostHandler handler) {
        return
            nest(path("/api/posts"),
                 nest(accept(MediaType.APPLICATION_JSON), 
                    route(method(HttpMethod.GET), handler::all)
                        .andRoute(DELETE("/{id}"), handler::deleteById)
                        .andRoute(method(HttpMethod.POST), handler::create)
                        .andRoute(PUT("/{id}"), handler::updateById)));
    }
}