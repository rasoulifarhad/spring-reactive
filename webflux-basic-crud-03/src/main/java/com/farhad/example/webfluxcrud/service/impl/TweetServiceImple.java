package com.farhad.example.webfluxcrud.service.impl;

import org.springframework.stereotype.Service;

import com.farhad.example.webfluxcrud.domain.Tweet;
import com.farhad.example.webfluxcrud.repositories.TweetRepository;
import com.farhad.example.webfluxcrud.service.TweetService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TweetServiceImple implements TweetService {

    private final TweetRepository repository ;

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<Tweet> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Tweet> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Tweet> save(Tweet tweet) {
        return  repository.save(tweet);
    }

    @Override
    public Mono<Tweet> updateById(String id, Tweet tweet) {
        return repository
                    .findById(id)
                    .flatMap(existingTweet -> {
                                tweet.setId(existingTweet.getId());
                                tweet.setCreatedAt(existingTweet.getCreatedAt());
                        return repository.save(tweet);
                    });
    }

    
    
}
