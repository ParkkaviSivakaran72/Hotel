package com.HotelBooking.Hotel.service.impl;

import java.util.List;

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
        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomiseException("User not Found!"));
            userRepository.deleteById(Long.valueOf(userId));
            
            response.setStatusCode(200);
            response.setMessage("Deleting a user Successfully ");
            
        }catch(CustomiseException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on deleting a user :" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();

        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserEntityToUserListDTO(userList);

            response.setUserList(userDTOList);

            response.setStatusCode(200);
            response.setMessage("Getting User details Successfully ");
            
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on register: :" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomiseException("User not Found!"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            
            response.setUser(userDTO);
            response.setStatusCode(200);
            response.setMessage("Getting my UserInfo Successfully ");
            
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on getting myInfo :" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomiseException("User not Found!"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOUserBookingAndRooms(user);

            response.setUser(userDTO);
            response.setStatusCode(200);
            response.setMessage("Getting booking details Successfully ");
            
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on getting all user :" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new CustomiseException("User not Found!"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setUser(userDTO);
            
            response.setStatusCode(200);
            response.setMessage("Getting User detail Successfully ");
            
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on deleting a user :" + e.getMessage());
        }

        return response;
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
            response.setExpirationTime("7 days");
            response.setRole(user.getRole());
            response.setMessage("SuccessFully loggedIn");

            response.setStatusCode(200);
            
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on Login: :" + e.getMessage());
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
            response.setMessage("Regsitered Successfully ");
            response.setUser(userDTO);
        }catch(CustomiseException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
             response.setStatusCode(400);
            response.setMessage("Error on register: :" + e.getMessage());
        }

        return response;
    }

}
