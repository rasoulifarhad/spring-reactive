package com.farhad.example.webfluxcrud.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Post;

import reactor.core.publisher.Mono;

@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {
    Mono<Boolean> existsByTitle(String title) ;
}
