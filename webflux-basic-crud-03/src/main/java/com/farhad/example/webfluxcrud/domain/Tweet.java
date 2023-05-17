package com.farhad.example.webfluxcrud.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "tweets")
public class Tweet {
    @Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text ;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();
}
