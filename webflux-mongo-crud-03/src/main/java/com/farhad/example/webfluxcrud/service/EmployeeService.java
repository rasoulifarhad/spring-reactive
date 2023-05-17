package com.farhad.example.webfluxcrud.service;

import com.farhad.example.webfluxcrud.dto.EmployeeDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> save(EmployeeDto employeeDto) ;
    Mono<EmployeeDto> byId(String employeeId) ;

    Flux<EmployeeDto> all();
    Mono<EmployeeDto> updateById(EmployeeDto employeeDto, String employeeId);

    Mono<Void> deleteById(String employeeId);
}
