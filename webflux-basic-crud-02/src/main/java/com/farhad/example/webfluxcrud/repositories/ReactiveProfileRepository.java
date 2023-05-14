package com.farhad.example.webfluxcrud.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Profile;

@Repository
public interface ReactiveProfileRepository extends ReactiveMongoRepository<Profile, String>{
    
}
