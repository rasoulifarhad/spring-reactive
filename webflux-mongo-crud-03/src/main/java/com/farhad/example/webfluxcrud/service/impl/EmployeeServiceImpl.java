package com.farhad.example.webfluxcrud.service.impl;

import org.springframework.stereotype.Service;

import com.farhad.example.webfluxcrud.dto.EmployeeDto;
import com.farhad.example.webfluxcrud.mapper.EmployeeMapper;
import com.farhad.example.webfluxcrud.repositories.EmployeeRepository;
import com.farhad.example.webfluxcrud.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public Flux<EmployeeDto> all() {
        return this.repository
                        .findAll()
                        .map(EmployeeMapper::mapToEmployeeDto)
                        .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> byId(String employeeId) {
        return this.repository
                        .findById(employeeId)
                        .map(EmployeeMapper::mapToEmployeeDto)
                        .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String employeeId) {
        return this.repository.deleteById(employeeId);
    }

    @Override
    public Mono<EmployeeDto> save(EmployeeDto employeeDto) {
        return Mono
                .just(EmployeeMapper.mapToEmployee(employeeDto))
                .flatMap(e ->  this.repository.save(e))
                .map(EmployeeMapper::mapToEmployeeDto);
    }

    @Override
    public Mono<EmployeeDto> updateById(EmployeeDto employeeDto, String employeeId) {
        return this.repository
                        .findById(employeeId)
                        .flatMap(e -> {
                            e.setFirstName(employeeDto.getFirstName());
                            e.setLastName(employeeDto.getLastName());
                            e.setEmail(employeeDto.getEmail());
                            return this.repository.save(e);
                        })
                        .map(EmployeeMapper::mapToEmployeeDto);
    }
    
    
}
