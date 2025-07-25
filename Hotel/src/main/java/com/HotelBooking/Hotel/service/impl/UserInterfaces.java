package com.HotelBooking.Hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HotelBooking.Hotel.dto.LoginRequest;
import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.dto.UserDTO;
import com.HotelBooking.Hotel.entity.User;
import com.HotelBooking.Hotel.exception.CustomiseException;
import com.HotelBooking.Hotel.repo.UserRepository;
import com.HotelBooking.Hotel.service.interfac.IUserInterface;
import com.HotelBooking.Hotel.util.JWTUtils;
import com.HotelBooking.Hotel.util.Utils;
import com.amazonaws.services.s3.model.S3DataSource;

@Service
public class UserInterfaces implements IUserInterface{

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtutils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response deleteUser(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getMyInfo(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getUserById(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CustomiseException("User not Found"));
            var token = jwtutils.generateToken(user);
            response.setToken(token);
            


            response.setStatusCode(200);
            
        }catch(CustomiseException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error : :" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response register(User user) {
        Response response = new Response();

        try {
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail((user.getEmail()))){
                throw new CustomiseException(user.getEmail() + " " + "Already Exists!");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setUser(userDTO);
        }catch(CustomiseException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error : :" + e.getMessage());
        }

        return response;
    }

}
