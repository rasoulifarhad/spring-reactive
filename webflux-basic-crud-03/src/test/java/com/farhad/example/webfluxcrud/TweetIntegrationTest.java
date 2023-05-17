package com.farhad.example.webfluxcrud;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.farhad.example.webfluxcrud.domain.Tweet;
import com.farhad.example.webfluxcrud.service.TweetService;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TweetIntegrationTest {
    
    @Autowired
    private TweetService tweetService;
    

    @Autowired
    private WebTestClient client;

    @Test
    public void createTweetTest() {
        Tweet tweet =Tweet.builder()
                            .text("Test tweet!")
                            .build();
        client  
            .post()
            .uri("/tweets")
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(tweet), Tweet.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty()
            .jsonPath("$.text").isEqualTo("Test tweet!");
    }

    @Test
    public void allTweetTest() {

        client
            .get()
            .uri("/tweets")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Tweet.class);
    }

    @Test
    public void findByIdTest() {
        Tweet tweet = tweetService.save(Tweet.builder().text("Test tweet!").build()).block();

        client
            .get()
            .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.text").isEqualTo("Test tweet!");

    }

    @Test
    public void deleteTweetTest() {

        Tweet tweet = tweetService.save(Tweet.builder().text("For delete Test tweet!").build()).block();

        client
            .delete()
            .uri("/tweets")
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void updateTweetByIdTest() {

        Tweet tweet = tweetService.save(Tweet.builder().text("For Update Test tweet!").build()).block();
        
        Tweet newTweet = Tweet.builder().text("Updated Test tweet!").build();

        client
            .put()
            .uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(newTweet),  Tweet.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.text").isEqualTo("Updated Test tweet");
    }

}
