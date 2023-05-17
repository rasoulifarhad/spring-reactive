package com.farhad.example.webfluxcrud.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * correspond to events which occur on a Show.  
 */
public class ShowEvent {
    private Integer _id;
    private String id;
    private Date date;
}
