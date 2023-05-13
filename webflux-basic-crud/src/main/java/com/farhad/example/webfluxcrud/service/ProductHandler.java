package com.farhad.example.webfluxcrud.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.domain.Product;
import com.farhad.example.webfluxcrud.repositories.ReactiveProductRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ProductHandler {
    
    private final ReactiveProductRepository repository;

    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        return  ServerResponse  
                        .ok()
                        .body(repository.findAll(), Product.class);
    }

    public Mono<ServerResponse> byId(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse
                        .ok()
                        .body(repository.findById(id), Product.class);
    }
}
