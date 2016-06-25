package com.retondar.repository;

import com.retondar.entity.ProvinceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.stream.Stream;

/**
 * Created by thiagoretondar on 24/06/16.
 */
public interface ProvinceRepository extends MongoRepository<ProvinceDocument, String> {


    @Query(value = "{ 'upperLeftX' : {$lte : ?0}, 'bottomRightX' : {$gte : ?0}, 'upperLeftY' : {$gte : ?1},'bottomRightY': {$lte : ?1} }")
    Stream<ProvinceDocument> findProvincesByPosition(int x, int y);

}
