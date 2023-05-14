package com.farhad.example.webfluxcrud.event;

import java.util.Date;

import com.farhad.example.webfluxcrud.domain.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileCreatedEvent {

    private final Profile profile ;
    private final Date date = new Date() ; 
    
}
