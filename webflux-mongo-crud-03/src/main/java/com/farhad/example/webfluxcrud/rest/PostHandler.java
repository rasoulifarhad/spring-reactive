package com.farhad.example.webfluxcrud.rest;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.dto.PostDto;
import com.farhad.example.webfluxcrud.service.PostService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostHandler {
    
    private final PostService postService ;

    public Mono<ServerResponse> all(ServerRequest request) {
        return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(postService.findAllPosts(), PostDto.class)
                    .switchIfEmpty(notFound());
            
                    
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request
                    .bodyToMono(PostDto.class)
                    .flatMap(p -> postService.save(p) )
                    .flatMap(this::created)
                    .switchIfEmpty(notFound());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return ServerResponse
                        .noContent()
                        .build(this.postService.deleteById(id(request)))
                        .switchIfEmpty(notFound());

    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        
        return request
                .bodyToMono(PostDto.class)
                .flatMap( p -> 
                    ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.postService.updateById(p, id(request)), PostDto.class)
                    )
                    .switchIfEmpty(notFound());
    }

    private  Mono<ServerResponse> created(PostDto postDto) {
        return ServerResponse
                    .created(URI.create("/api/posts/" + postDto.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(postDto), PostDto.class);
    }

    private  Mono<ServerResponse> notFound() {
        return  ServerResponse
                        .notFound()
                        .build();
    }

    private String id(ServerRequest serverRequest) {
        return serverRequest.pathVariable("id");
    }

}
