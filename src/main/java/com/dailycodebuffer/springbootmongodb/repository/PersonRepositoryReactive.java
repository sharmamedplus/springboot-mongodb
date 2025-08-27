package com.dailycodebuffer.springbootmongodb.repository;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PersonRepositoryReactive extends ReactiveMongoRepository<Person, String> {
    Flux<Person> findByFirstNameStartsWith(String name);
}
