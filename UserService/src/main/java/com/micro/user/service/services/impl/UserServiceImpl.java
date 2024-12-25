package com.micro.user.service.services.impl;

import com.micro.user.service.entities.Hotel;
import com.micro.user.service.entities.Rating;
import com.micro.user.service.entities.User;
import com.micro.user.service.exception.ResourceNotFoundException;
import com.micro.user.service.externals.HotelService;
import com.micro.user.service.repository.UserRepository;
import com.micro.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    // Injecting the UserRepository and RestTemplate beans
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    // Logger for logging purposes
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Save a new user in the repository
     * @param user User object to be saved
     * @return Saved user object
     */
    @Override
    public User saveUser(User user) {
        // Generate a random UserId and set it for the user
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);

        // Save the user to the repository and return it
        return userRepository.save(user);
    }

    /**
     * Get all users with their ratings and associated hotel details
     * @return List of all users with ratings and hotel details populated
     */
    @Override
    public List<User> getAllUser() {
        // Retrieve all users from the repository
        List<User> allUsers = userRepository.findAll();

        // Loop through each user to fetch ratings and associated hotel details
        for (User user : allUsers) {
            // Fetch ratings for the user from the Ratings microservice
            Rating[] ratings = restTemplate.getForObject(
                    "http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);

            // Convert the array of ratings to a list
            List<Rating> ratingsList = Arrays.asList(ratings);

            // Populate each rating with its associated hotel details
            for (Rating rating : ratingsList) {
                String hotelId = rating.getHotelId();
                Hotel hotel = restTemplate.getForObject(
                        "http://HOTELSERVICE/hotels/" + hotelId, Hotel.class);
                rating.setHotel(hotel);
            }

            // Set the fetched ratings to the user
            user.setRatings(ratingsList);
        }

        // Return the list of users with ratings and hotel details
        return allUsers;
    }

    /**
     * Get a single user by their ID with ratings and associated hotel details
     * @param userId User ID to fetch the user
     * @return User object with ratings and hotel details
     */
    @Override
    public User getUser(String userId) {
        // Fetch the user from the repository by ID
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with given id is not found on server: " + userId)
        );

        // Fetch ratings for the user from the Ratings microservice
        Rating[] ratings = restTemplate.getForObject(
                "http://RATINGSERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        // Convert the array of ratings to a list
        List<Rating> ratingsList = Arrays.asList(ratings);

        // Populate each rating with its associated hotel details
        for (Rating rating : ratingsList) {
            String hotelId = rating.getHotelId();
//            Hotel hotel = restTemplate.getForObject(
//                    "http://HOTELSERVICE/hotels/" + hotelId, Hotel.class);
            Hotel hotel = hotelService.getHotel(hotelId);
            rating.setHotel(hotel);
        }

        // Set the populated ratings list to the user
        user.setRatings(ratingsList);

        // Return the user object with ratings and hotel details
        return user;
    }
}
