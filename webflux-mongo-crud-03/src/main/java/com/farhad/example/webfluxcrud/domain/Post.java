package com.farhad.example.webfluxcrud.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Post {
    @Id
    private String id ;
    private String title;
    private String description;
    private String body;
    @Field(name = "created_on")
    private LocalDateTime createdOn;
    @Field(name = "updated_on")
    private LocalDateTime updatedOn;

}
