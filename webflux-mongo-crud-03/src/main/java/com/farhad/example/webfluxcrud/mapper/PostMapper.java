package com.farhad.example.webfluxcrud.mapper;

import org.springframework.stereotype.Component;

import com.farhad.example.webfluxcrud.domain.Post;
import com.farhad.example.webfluxcrud.dto.PostDto;

@Component
public class PostMapper {
    
    public Post mapToPost(PostDto postDto) {
        return Post.builder()
                        .id(postDto.getId())
                        .title(postDto.getTitle())
                        .description(postDto.getDescription())
                        .body(postDto.getBody())
                        .build();
    }

    public PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .body(post.getBody())
                        .build();
    }

}
