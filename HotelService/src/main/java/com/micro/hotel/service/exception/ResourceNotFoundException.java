package com.micro.hotel.service.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Hotel with given id not found !!");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }

}
