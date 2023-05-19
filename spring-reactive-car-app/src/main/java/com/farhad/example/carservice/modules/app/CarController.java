package com.farhad.example.carservice.modules.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.farhad.example.carservice.modules.domain.Car;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class CarController {
    
    private final WebClient carsClient = WebClient.create();
    //CarRequestService
    private final WebClient bookClient = WebClient.create();


    @PostMapping("/booking")
    public Mono<ResponseEntity<Car>> book() {
        return carsClient
                    .get()
                    .uri("/cars")
                    .retrieve()
                    .bodyToFlux(Car.class)
                    .doOnNext( car -> log.info("Trying to book: {}", car) )
                    .take(5)
                    .flatMap(this::requestCar)
                    .next()
                    .doOnNext(t -> log.info("Booked car: {}", t.getBody()))
                    ;
    }

    private Mono<ResponseEntity<Car>> requestCar(Car car) {
        return bookClient
                    .post()
                    .uri("/cars/{id}/booking", car.getId())
                    .retrieve()
                    .toEntity(Car.class);
    }
}
