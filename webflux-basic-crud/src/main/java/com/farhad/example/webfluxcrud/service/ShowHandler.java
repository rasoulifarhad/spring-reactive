package com.farhad.example.webfluxcrud.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.domain.Show;
import com.farhad.example.webfluxcrud.repositories.ReactiveShowRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ShowHandler {
    
    private final ReactiveShowRepository showRepository ;
    
    public Mono<ServerResponse> all(ServerRequest serverRequest) {
        Flux<Show> shows = this.showRepository.findAll();
        return ServerResponse.ok().body(shows, Show.class);
    }

    public Mono<ServerResponse> byId(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ServerResponse.ok().body(showRepository.findById(id), Show.class);
    }
}
