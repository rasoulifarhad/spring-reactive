package com.farhad.example.webfluxcrud.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.farhad.example.webfluxcrud.dto.EmployeeDto;
import com.farhad.example.webfluxcrud.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService service;

    @GetMapping
    public Flux<EmployeeDto> allEmployee() {
        return this.service.all();
    }

    @GetMapping("{id}")
    public Mono<EmployeeDto> byId(@PathVariable("id") String id ) {
        return service.byId(id);

    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> save(@RequestBody EmployeeDto employeeDto) {
        return this.service.save(employeeDto);
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateById(@RequestBody EmployeeDto employeeDto, @PathVariable("id") String id) {
        return this.service.updateById(employeeDto, id);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable("id") String id) {
        return this.service.deleteById(id);
    }
}
