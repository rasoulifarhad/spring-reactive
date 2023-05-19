package com.farhad.example.carservice.modules.requestservice;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class WebConfig {
    
    @Bean
    public RouterFunction<?> routes(CarRequestHandler handler) {
        return RouterFunctions.route(POST("/cars/{id}/booking"), handler::createBooking);
    }

}
