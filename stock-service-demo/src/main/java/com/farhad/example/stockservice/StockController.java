package com.farhad.example.stockservice;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;

@RestController
public class StockController {
    
    @GetMapping(value =  "/stocks/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockPrice> price(@PathVariable("symbol") String symbol) {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(l -> new StockPrice(symbol, randomStockPrice(), LocalDateTime.now() ));
    }

    private double randomStockPrice() {
        return ThreadLocalRandom.current().nextDouble(100.0);
    }
}
