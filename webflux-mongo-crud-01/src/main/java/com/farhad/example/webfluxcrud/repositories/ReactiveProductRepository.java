package com.farhad.example.webfluxcrud.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveProductRepository extends ReactiveMongoRepository<Product, String>{
    
    Flux<Product> findByName(String name) ;
    Flux<Product> findByName(Mono<String> name) ;
    Flux<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl) ;

    @Query("{'name': ?0, 'imageUrl': ?1}")
    Flux<Product> findByNameAndImageUrl(String name, String imageUrl) ;
}
