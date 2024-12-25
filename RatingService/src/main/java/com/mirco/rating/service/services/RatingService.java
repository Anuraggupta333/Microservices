package com.mirco.rating.service.services;

import com.mirco.rating.service.entity.Rating;

import java.util.List;

public interface RatingService {

    Rating create (Rating rating);

    List<Rating> getAllRating();

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);
}
