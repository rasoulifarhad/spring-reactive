package com.farhad.example.carservice.modules.locationservice;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.farhad.example.carservice.modules.domain.Car;
import com.farhad.example.carservice.modules.domain.LocationGenerator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class BootConfig {

    @Bean
    public CommandLineRunner initData(ReactiveMongoOperations mongo) {

        return args -> {

            LocationGenerator gen = new LocationGenerator(BigDecimal.valueOf(40.740900), BigDecimal.valueOf(-73.988000));

            mongo
                .dropCollection(Car.class)
                .then(Mono
                        .defer(() -> mongo.createCollection(Car.class)))
                .thenMany(
                    Flux
                        .range(1, 100)
                        .map( i -> new Car(i.longValue(), gen.location()) )
                        .flatMap(mongo::save))
                .blockLast();   
        };
    }
    
}
