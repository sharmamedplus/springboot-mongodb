package com.dailycodebuffer.springbootmongodb.service.reactive;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import com.dailycodebuffer.springbootmongodb.repository.PersonRepository;
import com.dailycodebuffer.springbootmongodb.repository.PersonRepositoryReactive;
import com.dailycodebuffer.springbootmongodb.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReactivePersonServiceImpl implements ReactivePersonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PersonRepositoryReactive personRepositoryReactive;

    @Override
    public Flux<Person> getPersonStartWithReactive(String name) {
        return personRepositoryReactive.findByFirstNameStartsWith(name);
    }
}
