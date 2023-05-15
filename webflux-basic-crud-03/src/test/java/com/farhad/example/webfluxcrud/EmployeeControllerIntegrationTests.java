package com.farhad.example.webfluxcrud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.farhad.example.webfluxcrud.dto.EmployeeDto;
import com.farhad.example.webfluxcrud.repositories.EmployeeRepository;
import com.farhad.example.webfluxcrud.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class EmployeeControllerIntegrationTests {
    
    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    public void beforeEach() {
        log.info("BeforeEach: deleting All employee");
        this.repository
                .deleteAll()
                .subscribe();
    }

    @Test
    public void saveTest() {
        EmployeeDto employeeDto = EmployeeDto
                                    .builder()
                                        .firstName("Farhad")
                                        .lastName("Rasouli")
                                        .email("rasouli.farhad@gmail.com")
                                    .build();
        client
            .post()
            .uri("api/employees")
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(employeeDto), EmployeeDto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.firstName").isEqualTo("Farhad")
            .jsonPath("$.lastName").isEqualTo("Rasouli")
            .jsonPath("$.email").isEqualTo("rasouli.farhad@gmail.com");
    }
}
