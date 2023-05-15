package com.farhad.example.webfluxcrud.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Employee;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String>{
    
}
