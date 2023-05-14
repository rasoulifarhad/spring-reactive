package com.farhad.example.webfluxcrud;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.repositories.ReactiveProfileRepository;
import com.farhad.example.webfluxcrud.service.ProfileService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Import(ProfileService.class)
@DataMongoTest
@RequiredArgsConstructor
public class ProfileServiceTest {
    
    private final ProfileService service;
    private final ReactiveProfileRepository repository;

    @Test
    public void getAllTest() {

        // Given
        
        Flux<Profile> saved = repository.saveAll(
                                            Flux
                                                .just("Farhad", "Ali", "Hassan")
                                                .map(s -> new Profile(null, s)));
        Flux<Profile> composite = service
                                        .all()
                                        .thenMany(saved);
        //When

        Predicate<Profile> match = p -> 
                                    saved.any(s -> s.equals(p))
                                            .block();

        // Then

        StepVerifier
            .create(composite)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .verifyComplete();
    }

}
