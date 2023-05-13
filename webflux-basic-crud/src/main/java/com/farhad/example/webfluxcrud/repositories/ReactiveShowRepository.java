package com.farhad.example.webfluxcrud.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Show;

@Repository
public interface ReactiveShowRepository extends ReactiveMongoRepository<Show, String>{ 

}
