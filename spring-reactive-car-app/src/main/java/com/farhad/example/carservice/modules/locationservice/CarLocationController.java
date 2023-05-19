package com.farhad.example.carservice.modules.locationservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.carservice.modules.domain.Car;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CarLocationController {
    
    private final CarRepository carRepository;

    @GetMapping("/cars")
    public Flux<Car> getCars() {
        return this.carRepository.findAll().log();
    }

    @GetMapping(path = "/cars", produces = "application/stream+json")
    public Flux<Car> getCarStream() {
        return this.carRepository.findCarsBy().log();
    }

    @PostMapping(path = "/cars", consumes = "application/stream+json" )
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> loadCars(@RequestBody Flux<Car> cars) {
        return this.carRepository.insert(cars).then();
        
    }
}
