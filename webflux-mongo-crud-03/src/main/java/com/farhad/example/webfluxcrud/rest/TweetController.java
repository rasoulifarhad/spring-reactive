package com.farhad.example.webfluxcrud.rest;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.webfluxcrud.domain.Tweet;
import com.farhad.example.webfluxcrud.service.TweetService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequiredArgsConstructor
public class TweetController {
    
    private final TweetService service;

    // Non-browser clients can request a stream of JSON by setting the Accept header to application/stream+json
    @GetMapping(value="/tweets")
    public Flux<Tweet> all() {
        return service.findAll();
    }

    @PostMapping(value="/tweets")
    public Mono<Tweet> createMono(@Valid @RequestBody Tweet tweet) {
       
        return service.save(tweet);
    }

    @GetMapping("/tweets/{id}")
    public Mono<ResponseEntity<Tweet>> byId(@PathVariable("id") String id ) {
        return  service
                    .findById(id)
                    .map( tweet -> 
                            ResponseEntity
                                    .ok()
                                    .body(tweet))
                    .defaultIfEmpty(ResponseEntity.notFound().build());   
    }

    @PutMapping("/tweets/{id}")
    public Mono<ResponseEntity<Tweet>> updateById(@PathVariable("id") String id, @Valid @RequestBody Tweet tweet) {
        return service
                    .updateById(id,tweet)
                    .map(t -> ResponseEntity.ok().body(t)  )
                    .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/tweets/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") String id) {
            return service
                    .deleteById(id)
                    .map(v -> 
                            ResponseEntity
                                .noContent()
                                .build());
    }

    // Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> streamAllTweets() {
        return service.findAll();

    }

    
}
