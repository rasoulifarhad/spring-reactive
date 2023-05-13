package com.farhad.example.webfluxcrud.service;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.domain.ShowEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ShowEventHandler {
    private AtomicInteger count = new AtomicInteger(0);
    public Mono<ServerResponse> events(ServerRequest serverRequest) {
        String showId = serverRequest.pathVariable("id");
        Flux<ShowEvent> events = Flux.<ShowEvent>generate(sink -> 
                                                    sink.next(new ShowEvent(count.incrementAndGet(), showId, new Date())) )
                                    .delaySequence(Duration.ofSeconds(1));
        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(events, ShowEvent.class);
    }
}
