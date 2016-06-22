package com.retondar.repository;

import com.retondar.entity.PropertyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by thiagoretondar on 19/06/16.
 */
public interface PropertyRepository extends MongoRepository<PropertyDocument, String> {

    @Query(value = "{ 'x': ?0, 'y' : ?1 }", count = true)
    int getQuantityPropertyInPosition(int x, int y);

}
