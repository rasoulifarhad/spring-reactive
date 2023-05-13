package com.farhad.example.webfluxcrud.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
/**
 * shows we are selling tickets for.
 */
public class Show {
    private String id ;
    private String title;
}
