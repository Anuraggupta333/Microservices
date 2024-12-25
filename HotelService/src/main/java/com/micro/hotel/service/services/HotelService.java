package com.micro.hotel.service.services;

import com.micro.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotel();

    Hotel getHotel(String id);

}
