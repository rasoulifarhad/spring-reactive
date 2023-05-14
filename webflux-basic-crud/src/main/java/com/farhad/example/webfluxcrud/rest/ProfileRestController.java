package com.farhad.example.webfluxcrud.rest;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.service.ProfileService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

// @RestController
@RequestMapping(value = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProfileRestController {

    private final ProfileService profileService;

    @GetMapping
    public Publisher<Profile> getAll() {
        return profileService.all();
    }

    @GetMapping("/{id}")
    Publisher<Profile> getById(@PathVariable("id") String id ) {
        return profileService.get(id);
    }

    @PostMapping
    public Publisher<ResponseEntity<Profile>> create(@RequestBody Profile profile) {
        return profileService
                    .create(profile.getEmail())
                    .map(p -> ResponseEntity.created( URI
                                                        .create( "/profiles/" + p.getId() ) )
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .build());
    }

    @DeleteMapping("/{id}")
    public Publisher<Profile> deleteById(@PathVariable("id") String id) {
        return profileService.delete(id);
    }

    @PutMapping("/{id}")
    public Publisher<ResponseEntity<Profile>> updateById(@PathVariable("id") String id, @RequestBody Profile profile) {
        return Mono     
                .just(profile)
                .flatMap(p -> profileService.update(id, p.getEmail()) )
                .map(p -> ResponseEntity
                                    .ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .build() );
    }
    
}
