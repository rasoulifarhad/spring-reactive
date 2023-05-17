package com.farhad.example.webfluxcrud.service;

import com.farhad.example.webfluxcrud.domain.Tweet;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TweetService {
    
    Flux<Tweet> findAll() ;
    Mono<Tweet> findById(String id);
    Mono<Tweet> save(Tweet tweet);
    Mono<Void>  deleteById(String id);

    Mono<Tweet> updateById(String id, Tweet tweet);
}
