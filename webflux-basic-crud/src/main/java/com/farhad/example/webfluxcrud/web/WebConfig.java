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

import com.farhad.example.webfluxcrud.service.ProductHandler;
import com.farhad.example.webfluxcrud.service.ProfileHandler;
import com.farhad.example.webfluxcrud.service.ShowEventHandler;
import com.farhad.example.webfluxcrud.service.ShowHandler;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    
    @Bean
    public RouterFunction<ServerResponse> routeShow(ShowHandler showHandler, ShowEventHandler showEventHandler) {
        return 
            route(GET("/shows/{id}/events"), showEventHandler::events)
            .andRoute(GET("/shows/{id}"), showHandler::byId)
            .andRoute(GET("/shows"), showHandler::all);
    }

    @Bean
    public RouterFunction<ServerResponse> routeProduct(ProductHandler productHandler) {

        return 
            route(GET("/products/{id}"), productHandler::byId)
            .andRoute(GET("/products"), productHandler::all);
    }

    @Bean
    public RouterFunction<ServerResponse> routeProfile(ProfileHandler profileHandler) {
        return
            route(GET("/profiles/{id}"), profileHandler::byId)
            .andRoute(DELETE("/profiles/{id}"), profileHandler::deleteById)
            .andRoute(GET("/profiles"), profileHandler::all)
            .andRoute(POST("/profiles"), profileHandler::create)
            .andRoute(PUT("/profiles/{id}"), profileHandler::updateById)
            ;
    }
}
