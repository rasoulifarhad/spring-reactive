package com.farhad.example.carservice.modules.app;

import java.math.BigDecimal;
import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.farhad.example.carservice.modules.domain.Car;
import com.farhad.example.carservice.modules.domain.LocationGenerator;

import reactor.core.publisher.Flux;

public class ClientUploadApp {
    public static void main(String[] args) {
        
        WebClient client = WebClient.create();

        LocationGenerator gen = new LocationGenerator(BigDecimal.valueOf(40.740900), BigDecimal.valueOf(-73.988000));

        Flux<Car> cars = Flux
                            .interval(Duration.ofSeconds(2))
                            .map(i -> new Car(i + 200, gen.location()));
        // CarLocationService                    
        client
            .post()
            .uri("/cars")
            .contentType(MediaType.APPLICATION_NDJSON)
            .body(cars, Car.class)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
