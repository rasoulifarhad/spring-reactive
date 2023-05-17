package com.farhad.example.webfluxcrud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.farhad.example.webfluxcrud.dto.PostDto;
import com.farhad.example.webfluxcrud.repositories.PostRepository;
import com.farhad.example.webfluxcrud.service.PostService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class PostHandlerIntegrationTests {
    
    @Autowired
    private PostService postService ;

    @Autowired 
    private PostRepository postRepository ;

    @Autowired
    private WebTestClient client ;

    @Test
    public void create_Post_Test() {
        PostDto postDto = PostDto
                            .builder()
                                .title("Title 01")
                                .description("Title 01 description")
                                .body("Title 01 body")
                            .build();
        client
            .post()
            .uri("api/posts")
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(postDto), PostDto.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .consumeWith(System.out::println)
            .jsonPath("$.title").isEqualTo("Title 01")
            .jsonPath("$.description").isEqualTo("Title 01 description")
            .jsonPath("$.body").isEqualTo("Title 01 body");

    }
}
