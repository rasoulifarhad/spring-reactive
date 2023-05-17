package com.farhad.example.webfluxcrud.service;

import com.farhad.example.webfluxcrud.dto.PostDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
    Mono<PostDto> save(PostDto postDto);

    Flux<PostDto> findAllPosts();

    Mono<PostDto> updateById(PostDto postDto, String id);

    Mono<Void> deleteById(String id);

    Mono<Boolean> postExistsWithTitle(String title) ;
}
