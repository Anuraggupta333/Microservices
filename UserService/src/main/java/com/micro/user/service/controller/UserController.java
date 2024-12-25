package com.micro.user.service.controller;

import com.micro.user.service.entities.User;
import com.micro.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Base URL mapping for all user-related endpoints
public class UserController {

    @Autowired
    private UserService userService; // Service layer dependency for user operations

    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // Logger for logging events

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user); // Save the user using the service
        return ResponseEntity.status(HttpStatus.CREATED).body(user1); // Return a 201 CREATED response
    }

    // Counter for retry demonstration (if enabled)
    int count = 1;

    // Get details of a single user by userId
    @GetMapping("/{userId}")
//    @CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback") // CircuitBreaker annotation for fault tolerance
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback") // Retry annotation for retrying failed calls
    @RateLimiter(name = "myRateLimiter", fallbackMethod = "ratingHotelFallback") // RateLimiter annotation for rate-limiting API calls
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
//        logger.info("Retry count: {}", count); // Log the retry count (if Retry is enabled)
//        count++;
        User user = userService.getUser(userId); // Fetch user details from the service
        return ResponseEntity.ok(user); // Return the user details in the response
    }

    // Fallback method for CircuitBreaker, Retry, and RateLimiter
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception) {
        logger.info("Fallback is called because the service is down: " + exception.getMessage()); // Log the fallback event
        User user = new User("000", "Dummy", "Dummy@gmail.com", "This is a dummy user"); // Create a dummy user as a fallback response
        return new ResponseEntity<>(user, HttpStatus.OK); // Return the dummy user with a 200 OK response
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser(); // Fetch all users from the service
        return ResponseEntity.ok(allUser); // Return the list of users in the response
    }
}
