package com.farhad.example.carservice.modules.locationservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;

import com.farhad.example.carservice.modules.domain.Car;

import reactor.core.publisher.Flux;

@Repository
public interface CarRepository extends ReactiveMongoRepository<Car, Long> {
    
    @Tailable
    Flux<Car> findCarsBy();
}
