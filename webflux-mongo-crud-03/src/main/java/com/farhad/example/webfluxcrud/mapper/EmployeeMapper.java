package com.farhad.example.webfluxcrud.mapper;

import com.farhad.example.webfluxcrud.domain.Employee;
import com.farhad.example.webfluxcrud.dto.EmployeeDto;

public class EmployeeMapper {
    
    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto
                    .builder()
                    .id(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .build();
    }

    public static Employee mapToEmployee(EmployeeDto dto) {
        return new Employee(dto.getId(), 
                            dto.getFirstName(), 
                            dto.getLastName(),
                            dto.getEmail());
    }

}
