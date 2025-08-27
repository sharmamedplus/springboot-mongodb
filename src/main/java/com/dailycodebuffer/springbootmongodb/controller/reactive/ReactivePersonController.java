package com.dailycodebuffer.springbootmongodb.controller.reactive;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import com.dailycodebuffer.springbootmongodb.service.reactive.ReactivePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/person/reactive")
public class ReactivePersonController {
    @Autowired
    private ReactivePersonService reactivePersonService;

    @GetMapping
    public Flux<Person> getPersonStartWithReactive(@RequestParam("name") String name) {
        return reactivePersonService.getPersonStartWithReactive(name);
    }
}
