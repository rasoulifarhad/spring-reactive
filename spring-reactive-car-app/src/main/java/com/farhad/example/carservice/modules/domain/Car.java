package com.farhad.example.carservice.modules.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Car {

    private final Long id ;
    private final Location location;

    @JsonCreator
    public Car(@JsonProperty("id") Long id, @JsonProperty("location") Location location) {
        this.id = id ;
        this.location = location ;
    }
}
