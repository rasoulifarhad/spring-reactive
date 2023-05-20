package com.farhad.example.stockservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.farhad.example.stockservice.client.WebClientStockClient;

import reactor.core.publisher.Flux;

// @SpringBootTest
public class WebClientStockClientIntegrationTest {
    

    private WebClient webClient = WebClient.builder().build();
    @Test
    public void shouldRetrieveStockPricesFromTheService() {
        //given
        WebClientStockClient webClientStockClient = new WebClientStockClient(webClient);

        //when 
        Flux<StockPrice> prices = webClientStockClient.priceFor("DEMO");

        //then
        Assertions.assertNotNull(prices);
        Assertions.assertTrue(prices.take(5).count().block() > 0) ;
    }

    @Test
    public void shouldRetrieveStockPricesFromTheService2() {
        //given
        WebClientStockClient webClientStockClient = new WebClientStockClient(webClient);

        //when 
        Flux<StockPrice> prices = webClientStockClient.priceFor("DEMO");

        //then
        Assertions.assertNotNull(prices);
        Flux<StockPrice> fivePrices =  prices.take(5);
        Assertions.assertEquals(5, fivePrices.count().block());
        Assertions.assertEquals("DEMO", fivePrices.blockFirst().getSymbol());
    }
}
