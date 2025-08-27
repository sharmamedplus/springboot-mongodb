package com.dailycodebuffer.springbootmongodb.service.reactive;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import reactor.core.publisher.Flux;

public interface ReactivePersonService {
    Flux<Person> getPersonStartWithReactive(String name);
}
