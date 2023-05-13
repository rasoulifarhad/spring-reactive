package com.farhad.example.webfluxcrud.repositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.farhad.example.webfluxcrud.domain.Show;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ReactiveShowRepository implements ReactiveCrudRepository<Show, String>{

    private final Map<String, Show> showsMap = new ConcurrentHashMap<>(); 

    public ReactiveShowRepository() {
        showsMap.put("1", new Show("1", "Title1"));
        showsMap.put("2", new Show("2", "Title2"));
        showsMap.put("3", new Show("3", "Title3"));
        showsMap.put("4", new Show("4", "Title4"));
        showsMap.put("5", new Show("5", "Title5"));
    }

    @Override
    public Mono<Long> count() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> delete(Show entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Show> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Show> entityStream) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends String> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<Show> findAll() {
        return Flux.fromIterable(showsMap.values());
    }

    @Override
    public Flux<Show> findAllById(Iterable<String> ids) {
        return null;
    }

    @Override
    public Flux<Show> findAllById(Publisher<String> idStream) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<Show> findById(String id) {
        return Mono.justOrEmpty(showsMap.get(id));
    }

    @Override
    public Mono<Show> findById(Publisher<String> id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Show> Mono<S> save(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Show> Flux<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Show> Flux<S> saveAll(Publisher<S> entityStream) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
