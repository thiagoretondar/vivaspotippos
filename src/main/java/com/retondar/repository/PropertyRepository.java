package com.retondar.repository;

import com.retondar.entity.PropertyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by thiagoretondar on 19/06/16.
 */
public interface PropertyRepository extends MongoRepository<PropertyDocument, String> {

    @Query(value = "{ 'x': ?0, 'y' : ?1 }", count = true)
    int getQuantityPropertyInPosition(int x, int y);

    @Query(value = "{ 'x' : {$gte : ?0, $lte : ?2}, 'y' : {$gte : ?3, $lte : ?1} }")
    List<PropertyDocument> findByArea(int xa, int ya, int xb, int yb);

}
