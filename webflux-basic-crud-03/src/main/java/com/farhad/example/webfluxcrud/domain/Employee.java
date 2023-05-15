package com.farhad.example.webfluxcrud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "employees")
public class Employee {
    
    @Id
    private String id ;
    private String firstName;
    private String lastName;
    private String email;
}
