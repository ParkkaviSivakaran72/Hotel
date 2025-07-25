package com.HotelBooking.Hotel.service.interfac;

import com.HotelBooking.Hotel.dto.LoginRequest;
import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.entity.User;

public interface IUserInterface {
    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
    
}
