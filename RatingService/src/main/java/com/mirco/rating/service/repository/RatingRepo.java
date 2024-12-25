package com.mirco.rating.service.repository;

import com.mirco.rating.service.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepo extends MongoRepository<Rating,String> {

//Custom Finder Method
    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String HotelId);


}
