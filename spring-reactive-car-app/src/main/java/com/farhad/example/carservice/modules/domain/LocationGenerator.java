package com.farhad.example.carservice.modules.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationGenerator {
    
    private static final MathContext MATH_CONTEXT = new MathContext(8);
    private static final Random random  = new Random();

    private final BigDecimal longitude;
    private final BigDecimal latitude;

    public Location location() {
        return new Location(longitude.add(randomDeviation(), MATH_CONTEXT), 
                            latitude.add(randomDeviation(), MATH_CONTEXT));
    }

    private BigDecimal randomDeviation() {
        return new BigDecimal((double)random.nextLong() % 100 / 1_000_000, MATH_CONTEXT);
    }
}
