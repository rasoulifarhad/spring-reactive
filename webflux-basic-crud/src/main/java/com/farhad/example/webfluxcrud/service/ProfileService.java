package com.farhad.example.webfluxcrud.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.event.ProfileCreatedEvent;
import com.farhad.example.webfluxcrud.repositories.ReactiveProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service 
@RequiredArgsConstructor
public class ProfileService {
    
    private final ApplicationEventPublisher publisher ;
    private final ReactiveProfileRepository repository;

    public Flux<Profile> all() {
        return this.repository.findAll();
    }

    public Mono<Profile> get(String id) {
        return this.repository.findById(id);
    }

    public Mono<Profile> update(String id, String email) {
        return repository
                .findById(id)
                .map(p -> new Profile(id, email))
                .flatMap(repository::save);
    }

    public Mono<Profile> delete(String id) {
        return repository
                .findById(id)
                .flatMap(p -> repository.deleteById(p.getId()).thenReturn(p) );
    }
    public Mono<Profile> create(String email) {
        return repository
                    .save(new Profile(null, email))
                    .doOnSuccess(p -> publisher.publishEvent(new ProfileCreatedEvent(p)))
                    ;
    }


}
