package com.micro.user.service.exception;

import com.micro.user.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandller {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
//        ApiResponse response = ApiResponse.builder()
//                .message(message)
//                .success(true)
//                .status(HttpStatus.NOT_FOUND)
//                .build();
        // Create ApiResponse object using constructor
        ApiResponse response = new ApiResponse(message, false, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
