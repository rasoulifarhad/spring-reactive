package com.farhad.example.webfluxcrud.init;

import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.repositories.ReactiveProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleProfileDataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    private final ReactiveProfileRepository repository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        repository
            .deleteAll()
            .thenMany(
                Flux
                    .just("A", "B", "C", "D")
                    .map(s -> new Profile(UUID.randomUUID().toString(), s + "@email.com"))
                    .flatMap(repository::save))
            .thenMany(repository.findAll())
            .subscribe(p -> log.info("Saving: {}", p) );
    }
    
}
