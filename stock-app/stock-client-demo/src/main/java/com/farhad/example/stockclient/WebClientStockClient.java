package com.farhad.example.stockclient;

import java.io.IOException;
import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

@Slf4j
@RequiredArgsConstructor
public class WebClientStockClient  implements StockClient {

    private final WebClient webClient;

    public Flux<StockPrice> pricesFor(String symbol) {
        return webClient
                    .get()
                    .uri("http://localhost:8080/stocks/{symbol}", symbol)
                    .retrieve()
                    .bodyToFlux(StockPrice.class)
                    .retryWhen(Retry.backoff(5, Duration.ofSeconds(1)).maxBackoff(Duration.ofSeconds(20)))
                    .doOnError(IOException.class,e -> log.error( e.getMessage() ))
                    ;
    }
}
