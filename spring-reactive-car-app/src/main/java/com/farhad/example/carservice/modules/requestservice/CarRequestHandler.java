package com.farhad.example.carservice.modules.requestservice;

import java.net.URI;
import java.time.Duration;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class CarRequestHandler {
    
    private static final Random RANDOM = new Random();
    public Mono<ServerResponse> createBooking(ServerRequest request) {
        return Mono
                .delay(randomThinkTime())
                .then(ServerResponse.created(bookingUrl(request)).build());
    }

    private Duration randomThinkTime() {
        return Duration.ofSeconds(RANDOM.nextInt( 5 - 2 ) + 2 );
    }

    private URI bookingUrl(ServerRequest request) {
        return URI.create("/cars/" + id(request) + "/booking/" + Math.abs(RANDOM.nextInt()));
    }
    private Long id(ServerRequest request) {
        return Long.parseLong(request.pathVariable("id"));
    }
}
