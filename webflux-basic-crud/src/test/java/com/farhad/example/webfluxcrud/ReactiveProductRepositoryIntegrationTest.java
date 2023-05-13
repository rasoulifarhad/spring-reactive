package com.farhad.example.webfluxcrud;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.farhad.example.webfluxcrud.domain.Product;
import com.farhad.example.webfluxcrud.repositories.ReactiveProductRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReactiveProductRepositoryIntegrationTest {
    
    @Autowired
    ReactiveProductRepository repository ;

    @Autowired
    ReactiveMongoOperations mongo;

    @BeforeEach
    public void setup() {
        mongo
            .collectionExists(Product.class)
            .flatMap(e -> e ? mongo.dropCollection(Product.class) : Mono.empty())
            .then(mongo.createCollection(Product.class))
            .thenMany(
                Flux
                    .fromIterable(getProducts())
                    .flatMap(p ->  mongo.save(p)))
            .thenMany(mongo.findAll(Product.class))
            .subscribe(p -> log.info("Saved: {}", p) );
    }

    private List<Product> getProducts() {
        return Arrays.asList(
                new Product("1","T Shirt-01", "Spring Guru printed T Shirt", new BigDecimal(125), "tshirt1-01.png"),
                new Product("2","T Shirt-01", "Spring Guru plain T Shirt", new BigDecimal(115), "tshirt2-01.png"),
                new Product("3","Mug-01", "Spring Guru printed Mug", new BigDecimal(39), "mug1-01.png"),
                new Product("4","Cap-01", "Spring Guru printed Cap", new BigDecimal(66), "cap1-01.png"));
        
    }

    @Test
    public void findByNameAndImageUrlWithStringQueryTest() {
        Flux<Product> flux =  repository.findByNameAndImageUrl("Mug-01", "mug1-01.png");

        StepVerifier
                .create(flux)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByNameAndImageUrlWithMonoQueryTest() {
        Flux<Product> flux =  repository.findByNameAndImageUrl(Mono.just("Mug-01"), "mug1-01.png");

        StepVerifier
                .create(flux)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByNameWithStringQueryTest() {
        Flux<Product> flux = repository.findByName("T Shirt-01");

        StepVerifier.create(flux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
    @Test
    public void findByNameWithMonoQueryTest() {
        Flux<Product> flux = repository.findByName(Mono.just("T Shirt-01"));

        StepVerifier.create(flux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

}

