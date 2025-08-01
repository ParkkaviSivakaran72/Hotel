package com.HotelBooking.Hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HotelBooking.Hotel.dto.LoginRequest;
import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.entity.User;
import com.HotelBooking.Hotel.service.impl.UserInterfaces;

@RestController
@RequestMapping("/auth")
public class Auth {

    @Autowired
    private UserInterfaces userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){
        Response response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

}
