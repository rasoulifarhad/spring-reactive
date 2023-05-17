package com.farhad.example.webfluxdemo;

import java.util.Random;

public enum Treatment {

    UPPER, LOWER, OFF;

    private static final Random PRNG =  new Random();
    
    public static Treatment randomTrreatment() {
        Treatment[] treatments = Treatment.values();
        return treatments[PRNG.nextInt(treatments.length)];
    }

}
