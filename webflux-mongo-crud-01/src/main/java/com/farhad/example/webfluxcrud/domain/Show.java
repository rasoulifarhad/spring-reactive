package com.farhad.example.webfluxcrud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
/**
 * shows we are selling tickets for.
 */
public class Show {
    @Id
    private String id ;
    private String title;
}
