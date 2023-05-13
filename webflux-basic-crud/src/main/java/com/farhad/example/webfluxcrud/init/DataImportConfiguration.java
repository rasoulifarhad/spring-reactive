package com.farhad.example.webfluxcrud.init;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

import com.farhad.example.webfluxcrud.domain.Product;
import com.farhad.example.webfluxcrud.domain.Show;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * This class loads a YAML file and uses it to create elements to insert into the database. It’s not that 
 * typical to see code like this, but it’ll help to demonstrate migration to the new Binder API later.
 */
@Configuration
@Slf4j
public class DataImportConfiguration {
    
    @Bean
    @Order(300)
    public CommandLineRunner initData(ReactiveMongoOperations mongo) {
        return args -> {
            mongo
                .dropCollection(Show.class)
                .then(mongo.createCollection(Show.class))
                .thenMany(
                    Flux    
                        .fromIterable(getShows())
                        .flatMap(s -> mongo.save(s)))
                // .log()
                // .subscribe(
                //     null, 
                //     null, 
                //     () -> log.info("done initialization...")
                // );
                .log()
                .thenMany(mongo.findAll(Show.class))
                .subscribe(s -> log.info("saved: {}", s));
        };
    }
    private List<Show> getShows() {
        Properties yaml = loadShowsYaml();
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(yaml);
        return new Binder(source).bind("shows", Bindable.listOf(Show.class)).get();
    }

    private Properties loadShowsYaml() {
        YamlPropertiesFactoryBean properties = new YamlPropertiesFactoryBean();
        properties.setResources(new ClassPathResource("shows.yml"));
        return properties.getObject();
    }  

    @Bean
    @Order(400)
    public CommandLineRunner initProduts(ReactiveMongoOperations mongo) {
        return args -> {
            mongo
                .dropCollection(Product.class)
                .then(mongo.createCollection(Product.class))
                .thenMany(
                    Flux
                        .fromIterable(getProducts())
                        .flatMap(p -> mongo.save(p)))
                .log()
                .thenMany(mongo.findAll(Product.class))
                .subscribe(p -> log.info("Saved: {}", p) );
        };
    }


    private List<Product> getProducts() {
        Properties yaml = loadProductsYaml();
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(yaml);
        return new Binder(source).bind("products", Bindable.listOf(Product.class)).get();
    }

    private Properties loadProductsYaml() {
        YamlPropertiesFactoryBean properties = new YamlPropertiesFactoryBean();
        properties.setResources(new ClassPathResource("products.yml"));
        return properties.getObject();
    }
    // /**
    //  * curl -s -XGET http://localhost:8080/shows | jq '.'
    //  * curl -s -XGET http://localhost:8080/shows/{id} | jq '.'
    //  */
    // @Bean
    // @Order(400)
    // public CommandLineRunner testInitData(ReactiveMongoOperations mongo) {
    //     return  args -> {
    //         log.info("Customers found with findAll():");
    //         mongo
    //             .findAll(Show.class)
    //             .subscribe( s -> log.info("{}", s) )
    //             ;
    //     };
    // }

    // @Bean
    // public CommandLineRunner anotherInitData(ReactiveShowRepository shows) {
    //     return args ->  {
    //         shows
    //             .deleteAll()
    //             .thenMany(
    //                 Flux
    //                     .just("Title 8", "Title 9", "Title 10", "Title 11")
    //                 //    .fromIterable(list)
    //                    .flatMap(
    //                         t -> shows.save(Show.builder().title(t).build())
    //                    )
    //             )
    //             .log()
    //             .subscribe(
    //                 null,
    //                 null,
    //                 () -> log.info("done initialization...")
    //             );
    //     };
    // }
}
