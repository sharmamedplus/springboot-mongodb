package com.dailycodebuffer.springbootmongodb.repository;

import com.dailycodebuffer.springbootmongodb.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  PersonRepository extends MongoRepository<Person,String> {

    List<Person> findByFirstNameStartsWith(String name);

    //List<Person> findByAgeBetween(Integer min, Integer max);

    @Query(value = "{ 'age' : { $gt : ?0, $lt : ?1}}",
           fields = "{addresses:  0}")  // projection to exclude addresses field
    List<Person> findPersonByAgeBetween(Integer min, Integer max);


        // $eq
        @Query("{ 'age': { '$eq': ?0 } }")
        List<Person> findByAgeEquals(Integer age);

        // $gt
        @Query("{ 'age': { '$gt': ?0 } }")
        List<Person> findByAgeGreaterThan(Integer age);

        // $gte
        @Query("{ 'age': { '$gte': ?0 } }")
        List<Person> findByAgeGreaterThanOrEqual(Integer age);

        // $lt
        @Query("{ 'age': { '$lt': ?0 } }")
        List<Person> findByAgeLessThan(Integer age);

        // $lte
        @Query("{ 'age': { '$lte': ?0 } }")
        List<Person> findByAgeLessThanOrEqual(Integer age);

        // $ne
        @Query("{ 'age': { '$ne': ?0 } }")
        List<Person> findByAgeNot(Integer age);

        // $in
        @Query("{ 'firstName': { '$in': ?0 } }")
        List<Person> findByFirstNameIn(List<String> names);

        // $nin
        @Query("{ 'firstName': { '$nin': ?0 } }")
        List<Person> findByFirstNameNotIn(List<String> names);

        // $and
        @Query("{ '$and': [ { 'age': { '$gt': ?0 } }, { 'city': ?1 } ] }")
        List<Person> findByAgeGreaterThanAndCity(Integer age, String city);

        // $or
        @Query("{ '$or': [ { 'age': ?0 }, { 'city': ?1 } ] }")
        List<Person> findByAgeOrCity(Integer age, String city);

        // $not
        @Query("{ 'age': { '$not': { '$gt': ?0 } } }")
        List<Person> findByAgeNotGreaterThan(Integer age);

        // $nor
        @Query("{ '$nor': [ { 'age': ?0 }, { 'city': ?1 } ] }")
        List<Person> findByNotAgeNorCity(Integer age, String city);

        // $exists
        @Query("{ 'email': { '$exists': true } }")
        List<Person> findByEmailExists();

        // $type
        @Query("{ 'age': { '$type': 16 } }") // 16 = int32
        List<Person> findByAgeTypeInt();

        // $expr
        @Query("{ '$expr': { '$gt': [ '$age', 30 ] } }")
        List<Person> findByExprAgeGreaterThan30();

        // $regex
        @Query("{ 'firstName': { '$regex': ?0, '$options': 'i' } }")
        List<Person> findByFirstNameRegex(String pattern);

        // $all
        @Query("{ 'hobbies': { '$all': ?0 } }")
        List<Person> findByAllHobbies(List<String> hobbies);

        // $elemMatch
        @Query("{ 'addresses': { '$elemMatch': { 'city': ?0 } } }")
        List<Person> findByAddressCity(String city);

        // $size
        @Query("{ 'hobbies': { '$size': ?0 } }")
        List<Person> findByHobbiesSize(int size);

        // $mod
        @Query("{ 'age': { '$mod': [ ?0, ?1 ] } }")
        List<Person> findByAgeMod(int divisor, int remainder);

        // $text
        @Query("{ '$text': { '$search': ?0 } }")
        List<Person> findByTextSearch(String text);

        // $where
        @Query("{ '$where': ?0 }")
        List<Person> findByWhere(String jsCondition);

        // $geoWithin
        @Query("{ 'location': { '$geoWithin': { '$centerSphere': [ [ ?0, ?1 ], ?2 ] } } }")
        List<Person> findByLocationWithin(double lng, double lat, double radius);

        // $near
        @Query("{ 'location': { '$near': { '$geometry': { 'type': 'Point', 'coordinates': [ ?0, ?1 ] }, '$maxDistance': ?2 } } }")
        List<Person> findByLocationNear(double lng, double lat, double maxDistance);

        // $exists with false
        @Query("{ 'phone': { '$exists': false } }")
        List<Person> findByPhoneNotExists();
    }



    // $eq, $gt, $gte, $lt, $lte, $ne, $in, $nin, $and, $or, $not, $nor, $exists, $type, $expr,
// $jsonSchema, $mod, $regex, $text, $where, $geoIntersects, $geoWithin, $near, $nearSphere,
// $all, $elemMatch, $size, $slice, $meta, $rand, $sample, $unionWith, $lookup, $graphLookup,
// $facet, $bucket, $bucketAuto, $sortByCount, $addFields, $set, $unset, $replaceRoot,
// $replaceWith, $count, $project, $match, $group, $sort, $limit, $skip, $out, $indexStats,
// $collStats, $currentOp, $listSessions, $planCacheStats, $listCollections, $listIndexes, $listDatabases,
// $dbStats, $shardCollection, $splitVector, $moveChunk, $removeShardFromZone, $addShardToZone, $updateZoneKeyRange,
// $getShardMap, $getShardVersion, $getLastError, $ping, $isMaster, $buildInfo, $serverStatus, $hostInfo, $whatsmyuri,
// $listCommands, $getLog, $getParameter, $setParameter, $replSetGetStatus, $replSetGetConfig, $replSetInitiate,
// $replSetReconfig, $replSetStepUp, $replSetFreeze, $replSetMaintenance, $replSetSyncFrom, $replSetResizeOplog

