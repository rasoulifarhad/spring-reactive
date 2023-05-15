package com.farhad.example.webfluxcrud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec;

import com.farhad.example.webfluxcrud.dto.EmployeeDto;
import com.farhad.example.webfluxcrud.mapper.EmployeeMapper;
import com.farhad.example.webfluxcrud.repositories.EmployeeRepository;
import com.farhad.example.webfluxcrud.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

    @Test
    public void getEmployeeByIdTest() {
        EmployeeDto employeeDto = EmployeeDto
                                    .builder()
                                        .firstName("Farhad")
                                        .lastName("Rasouli")
                                        .email("rasouli.farhad@gmail.com")
                                    .build();
        
        EmployeeDto savedEmployee =  Mono
                                        .just(employeeDto)
                                        .map(EmployeeMapper::mapToEmployee)
                                        .flatMap(e -> repository.save(e) )
                                        .map(EmployeeMapper::mapToEmployeeDto)
                                        .block();
        client
            .get()
            .uri("api/employees/" + savedEmployee.getId() )
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(System.out::println)
            .jsonPath("$.id").isEqualTo(savedEmployee.getId())
            .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
            .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
            .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    public void employeeAllTest() {

        EmployeeDto employeeDto = EmployeeDto
                                    .builder()
                                        .firstName("Farhad")
                                        .lastName("Rasouli")
                                        .email("rasouli.farhad@gmail.com")
                                    .build();
        EmployeeDto anotherEmployeeDto = EmployeeDto
                                    .builder()
                                        .firstName("xxxx")
                                        .lastName("yyyy")
                                        .email("yyyy.xxxx@gmail.com")
                                    .build();
        List<EmployeeDto> savedEmployees =  Flux
                                            .just(employeeDto, anotherEmployeeDto)
                                            .map(EmployeeMapper::mapToEmployee)
                                            .flatMap(e -> repository.save(e) )
                                            .map(EmployeeMapper::mapToEmployeeDto)
                                            .collectList()
                                            .block();
        ListBodySpec<EmployeeDto> listBodySpec = 
        client
            .get()
            .uri("api/employees")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(EmployeeDto.class)
            .consumeWith(System.out::println)
            ;
            
        assertThat(listBodySpec.returnResult().getResponseBody()).hasSize(2);
        assertThat(listBodySpec.returnResult().getResponseBody()).containsAnyElementsOf(savedEmployees);
        assertThat(listBodySpec.returnResult().getResponseBody()).containsAll(savedEmployees);
    }

    @Test
    public void employeeUpdateByIdTest() {

        EmployeeDto emp = EmployeeDto
                        .builder()
                            .firstName("Farhad")
                            .lastName("Rasouli")
                            .email("rasouli.farhad@gmail.com")
                        .build();
        EmployeeDto  savedEmployee =  Mono
                                        .just(emp)
                                        .map(EmployeeMapper::mapToEmployee)
                                        .flatMap(e -> repository.save(e) )
                                        .map(EmployeeMapper::mapToEmployeeDto)
                                        .block();


        EmployeeDto updatedEmployeeDto = EmployeeDto
                                    .builder()
                                        .firstName("xxxx")
                                        .lastName("yyyy")
                                        .email("yyyy.xxxx@gmail.com")
                                    .build();

        client
            .put()
            .uri("api/employees/" + savedEmployee.getId())
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(updatedEmployeeDto), EmployeeDto.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.firstName").isEqualTo("xxxx")
            .jsonPath("$.lastName").isEqualTo("yyyy")
            .jsonPath("$.email").isEqualTo("yyyy.xxxx@gmail.com")
            ;
    }

}
