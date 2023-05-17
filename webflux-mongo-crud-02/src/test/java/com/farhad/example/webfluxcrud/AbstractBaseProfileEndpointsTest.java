package com.farhad.example.webfluxcrud;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.repositories.ReactiveProfileRepository;
import com.farhad.example.webfluxcrud.rest.ProfileRestController;
import com.farhad.example.webfluxcrud.service.ProfileHandler;
import com.farhad.example.webfluxcrud.service.ProfileService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ProfileRestController.class)
@ExtendWith(SpringExtension.class)
@Import({ProfileService.class, ProfileHandler.class})
public class AbstractBaseProfileEndpointsTest {

    @Autowired
    private  WebTestClient client;

    @MockBean
    private ReactiveProfileRepository repository;


    @Test
    public void getAllTest() {

        Mockito
            .when(this.repository.findAll())
            .thenReturn(Flux.just(new Profile("1", "A"), new Profile("2", "B")));

        client
            .get()
            .uri("/profiles")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[0].id").isEqualTo("1")
            .jsonPath("$.[0].email").isEqualTo("A")
            .jsonPath("$.[1].id").isEqualTo("2")
            .jsonPath("$.[1].email").isEqualTo("B");
    }

    @Test
    public void saveTest() {

        Profile profile = new Profile("1234", UUID.randomUUID().toString() + "@email.com");

        Mockito
            .when(this.repository.save(Mockito.any(Profile.class)))
            .thenReturn(Mono.just(profile));
        
        client
            .post()
            .uri("/profiles")
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(profile), Profile.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void deleteByIdTest() {

        Profile profile = new Profile("1234", UUID.randomUUID().toString() + "@email.com");

        Mockito
            .when(this.repository.findById(profile.getId()))
            .thenReturn(Mono.just(profile));

        Mockito
            .when(this.repository.deleteById(profile.getId()))
            .thenReturn(Mono.empty());

        client
            .delete()
            .uri("/profiles/" + profile.getId())
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void updateByIdTest() {
        Profile profile = new Profile("1234", UUID.randomUUID().toString() + "@email.com");

        Mockito
            .when(this.repository.findById(profile.getId()))
            .thenReturn(Mono.just(profile));
        
        Mockito
            .when(this.repository.save(profile))
            .thenReturn(Mono.just(profile));

        client
            .put()
            .uri("/profiles/" + profile.getId())
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(profile), Profile.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void getById() {
        Profile profile = new Profile("1234", UUID.randomUUID().toString() + "@email.com");

        Mockito
            .when(this.repository.findById(profile.getId()))
            .thenReturn(Mono.just(profile));

        client
            .get()
            .uri("/profiles/" + profile.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(profile.getId())
            .jsonPath("$.email").isEqualTo(profile.getEmail());
    }
}
