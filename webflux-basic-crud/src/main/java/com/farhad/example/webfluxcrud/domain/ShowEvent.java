package com.farhad.example.webfluxcrud.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
/**
 * correspond to events which occur on a Show.  
 */
public class ShowEvent {
    private Integer _id;
    private String id;
    private Date date;
}
