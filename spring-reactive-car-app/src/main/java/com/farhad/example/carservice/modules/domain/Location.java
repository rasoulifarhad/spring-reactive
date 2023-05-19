package com.farhad.example.carservice.modules.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Location {
    private final BigDecimal longitude;
    private final BigDecimal latitude;

    @JsonCreator
    public Location(@JsonProperty("longitude") BigDecimal longitude, @JsonProperty("latitude") BigDecimal latitude) {

        this.longitude = longitude ;
        this.latitude = latitude;
    }
}
