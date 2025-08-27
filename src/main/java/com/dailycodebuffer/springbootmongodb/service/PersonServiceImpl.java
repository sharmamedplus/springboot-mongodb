package com.dailycodebuffer.springbootmongodb.service;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import com.dailycodebuffer.springbootmongodb.repository.PersonRepository;
import com.dailycodebuffer.springbootmongodb.repository.PersonRepositoryReactive;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
        return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getByPersonAge(Integer minAge, Integer maxAge) {
        return personRepository.findPersonByAgeBetween(minAge,maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if(name !=null && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name,"i"));
        }

        if(minAge !=null && maxAge !=null) {
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if(city !=null && !city.isEmpty()) {
            criteria.add(Criteria.where("addresses.city").is(city));
        }

        if(!criteria.isEmpty()) {
            query.addCriteria(new Criteria()
                    .andOperator(criteria.toArray(new Criteria[0])));
        }

        Page<Person> people = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Person.class
                ), pageable, () -> mongoTemplate.count(query.skip(0).limit(0),Person.class));
        return people;
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        UnwindOperation unwindOperation
                = Aggregation.unwind("addresses");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC,"age");
        GroupOperation groupOperation
                = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestPerson");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);

        List<Document> person
                = mongoTemplate.aggregate(aggregation, Person.class,Document.class).getMappedResults();
        return person;
    }

    @Override
    public List<Document> getPopulationByCity() {

        UnwindOperation unwindOperation
                = Aggregation.unwind("addresses");
        GroupOperation groupOperation
                = Aggregation.group("addresses.city")
                .count().as("popCount");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC, "popCount");

        ProjectionOperation projectionOperation
                = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation,groupOperation,sortOperation,projectionOperation);

        List<Document> documents
                = mongoTemplate.aggregate(aggregation,
                Person.class,
                Document.class).getMappedResults();
        return  documents;
    }


        public List<Person> getAllPersons() {
            return personRepository.findAll();
        }

        public Person savePerson(Person person) {
            return personRepository.save(person);
        }

        public List<Person> findByFirstNameStartsWith(String name) {
            return personRepository.findByFirstNameStartsWith(name);
        }

        public List<Person> findPersonByAgeBetween(Integer min, Integer max) {
            return personRepository.findPersonByAgeBetween(min, max);
        }

        public List<Person> findByAgeEquals(Integer age) {
            return personRepository.findByAgeEquals(age);
        }

        public List<Person> findByAgeGreaterThan(Integer age) {
            return personRepository.findByAgeGreaterThan(age);
        }

        public List<Person> findByAgeGreaterThanOrEqual(Integer age) {
            return personRepository.findByAgeGreaterThanOrEqual(age);
        }

        public List<Person> findByAgeLessThan(Integer age) {
            return personRepository.findByAgeLessThan(age);
        }

        public List<Person> findByAgeLessThanOrEqual(Integer age) {
            return personRepository.findByAgeLessThanOrEqual(age);
        }

        public List<Person> findByAgeNot(Integer age) {
            return personRepository.findByAgeNot(age);
        }

        public List<Person> findByFirstNameIn(List<String> names) {
            return personRepository.findByFirstNameIn(names);
        }

        public List<Person> findByFirstNameNotIn(List<String> names) {
            return personRepository.findByFirstNameNotIn(names);
        }

        public List<Person> findByAgeGreaterThanAndCity(Integer age, String city) {
            return personRepository.findByAgeGreaterThanAndCity(age, city);
        }

        public List<Person> findByAgeOrCity(Integer age, String city) {
            return personRepository.findByAgeOrCity(age, city);
        }

        public List<Person> findByAgeNotGreaterThan(Integer age) {
            return personRepository.findByAgeNotGreaterThan(age);
        }

        public List<Person> findByNotAgeNorCity(Integer age, String city) {
            return personRepository.findByNotAgeNorCity(age, city);
        }

        public List<Person> findByEmailExists() {
            return personRepository.findByEmailExists();
        }

        public List<Person> findByAgeTypeInt() {
            return personRepository.findByAgeTypeInt();
        }

        public List<Person> findByExprAgeGreaterThan30() {
            return personRepository.findByExprAgeGreaterThan30();
        }

        public List<Person> findByFirstNameRegex(String pattern) {
            return personRepository.findByFirstNameRegex(pattern);
        }

        public List<Person> findByAllHobbies(List<String> hobbies) {
            return personRepository.findByAllHobbies(hobbies);
        }

        public List<Person> findByAddressCity(String city) {
            return personRepository.findByAddressCity(city);
        }

        public List<Person> findByHobbiesSize(int size) {
            return personRepository.findByHobbiesSize(size);
        }

        public List<Person> findByAgeMod(int divisor, int remainder) {
            return personRepository.findByAgeMod(divisor, remainder);
        }

        public List<Person> findByTextSearch(String text) {
            return personRepository.findByTextSearch(text);
        }

        public List<Person> findByWhere(String jsCondition) {
            return personRepository.findByWhere(jsCondition);
        }

        public List<Person> findByLocationWithin(double lng, double lat, double radius) {
            return personRepository.findByLocationWithin(lng, lat, radius);
        }

        public List<Person> findByLocationNear(double lng, double lat, double maxDistance) {
            return personRepository.findByLocationNear(lng, lat, maxDistance);
        }

        public List<Person> findByPhoneNotExists() {
            return personRepository.findByPhoneNotExists();
        }
}
