package com.farhad.example.webfluxcrud.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.farhad.example.webfluxcrud.domain.Post;
import com.farhad.example.webfluxcrud.dto.PostDto;
import com.farhad.example.webfluxcrud.mapper.PostMapper;
import com.farhad.example.webfluxcrud.repositories.PostRepository;
import com.farhad.example.webfluxcrud.service.PostService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final PostMapper mapper ;

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<PostDto> findAllPosts() {
        return repository
                    .findAll()
                    .map(mapper::mapToPostDto)
                    .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<PostDto> save(PostDto postDto) {
        Post post =mapper.mapToPost(postDto);
        post.setCreatedOn(LocalDateTime.now());
        post.setUpdatedOn(LocalDateTime.now());
        return Mono
                .just(post)
                .flatMap(p -> repository.save(p) )
                .map(mapper::mapToPostDto);
    }

    @Override
    public Mono<PostDto> updateById(PostDto postDto, String id) {
        return repository
                    .findById(id)
                    .flatMap(p -> {
                        Post post = mapper.mapToPost(postDto);
                        post.setId(p.getId());
                        post.setCreatedOn(p.getCreatedOn());
                        post.setUpdatedOn(LocalDateTime.now());
                        return repository.save(post) ;
                    })
                    .map(mapper::mapToPostDto);
    }

    @Override
    public Mono<Boolean> postExistsWithTitle(String title) {
        return repository.existsByTitle(title);
    }
    
}
