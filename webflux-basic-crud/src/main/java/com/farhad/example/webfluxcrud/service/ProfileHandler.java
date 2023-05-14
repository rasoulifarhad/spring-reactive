package com.farhad.example.webfluxcrud.service;

import java.net.URI;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.farhad.example.webfluxcrud.domain.Profile;
import com.farhad.example.webfluxcrud.repositories.ReactiveProfileRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProfileHandler {

    private final ReactiveProfileRepository repository ;
    private final ProfileService profileService;

    public Mono<ServerResponse> byId(ServerRequest request) {
        return defaultReadResponse(profileService.get(id(request)));
        // return ServerResponse
        //                 .ok()
        //                 .body( this.repository.findById(request.pathVariable("id")), Profile.class);
    }
    public Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(this.profileService.all());
        // return  ServerResponse
        //             .ok()
        //             .body( this.repository.findAll(), Profile.class);
    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        Mono<Profile> updatedProfile = 
                        request
                            .bodyToMono(Profile.class)
                            .flatMap(
                                p -> this.profileService.update(id(request), p.getEmail()));
        return defaultReadResponse(updatedProfile);
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return defaultReadResponse(this.profileService.delete(id(request)));
    }
    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<Profile> profile = request
                                           .bodyToMono(Profile.class)
                                           .flatMap(p -> this.profileService.create(p.getEmail()));
        return defaultWriteResponse(profile);
    }


    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Profile> profiles) {
        return Mono 
                .from(profiles)
                .flatMap(
                    p -> ServerResponse
                                .created(URI.create("/profiles" + p.getId())) 
                                .contentType(MediaType.APPLICATION_JSON)
                                .build());
    }
    
    private static Mono<ServerResponse> defaultReadResponse(Publisher<Profile> profiles) {
        return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(profiles, Profile.class);

    }
    private static String id(ServerRequest serverRequest) {
        return serverRequest.pathVariable("id");
    }

}

