package com.HotelBooking.Hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.dto.RoomDTO;
import com.HotelBooking.Hotel.entity.Room;
import com.HotelBooking.Hotel.exception.CustomiseException;
import com.HotelBooking.Hotel.repo.BookingRepository;
import com.HotelBooking.Hotel.repo.RoomRepository;
import com.HotelBooking.Hotel.service.interfac.IRoomInterface;
import com.HotelBooking.Hotel.util.FileUploadUtil;
import com.HotelBooking.Hotel.util.Utils;
import com.cloudinary.Cloudinary;

@Service    
public class RoomService implements IRoomInterface {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired 
    private Cloudinary cloudinary;
    
    
    
    @Override
    public Response addNewRoom(MultipartFile photo,MultipartFile file,  String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        FileUploadUtil.assertAllowed(photo, FileUploadUtil.IMAGE_PATTERN);
        try {
            final String publicId = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            final Map<String, String> params = new HashMap<>();
            params.put("public_id",FileUploadUtil.getFileName(file.getOriginalFilename()));
            final Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
            final String url = (String) uploadResult.get("secure_url");
            final String public_id = (String) uploadResult.get("publicId");

            Room room = new Room();
            room.setRoomPhotoURL(url);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setDescription(description);
            Room savedRoom = roomRepository.save(room);

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

            response.setRoom(roomDTO);
            response.setMessage("Successfully added room");
            response.setStatusCode(200);   
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting a user info" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try {
            roomRepository.findById(roomId).orElseThrow(() -> new CustomiseException("Room not found"));
            roomRepository.deleteById(roomId);

            response.setMessage("Successfully got all rooms");
            response.setStatusCode(200);  
            
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error on deleting room" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
       Response response = new Response();
        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.maproomEntityToRoomListDTO(roomList);

            response.setMessage("Successfully got all available rooms");
            response.setStatusCode(200);  
            response.setRoomList(roomDTOList); 
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting available rooms" + e.getMessage());

        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDTO> roomDTOList = Utils.maproomEntityToRoomListDTO(roomList);

            response.setMessage("Successfully got all rooms");
            response.setStatusCode(200);  
            response.setRoomList(roomDTOList); 
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting rooms" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try {
            List<Room> availableRooms = roomRepository.findavailableRoomsByDateAndTimes(checkInDate,checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.maproomEntityToRoomListDTO(availableRooms);
             
            response.setMessage("Successfully got all rooms by date and types");
            response.setStatusCode(200);  
            response.setRoomList(roomDTOList); 
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting rooms by date and types" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomiseException("Room not found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOBookings(room);
            response.setMessage("Successfully got room by id");
            response.setStatusCode(200);  
            response.setRoom(roomDTO); 
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting rooms" + e.getMessage());

        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice,
            MultipartFile photo,MultipartFile file) {
        Response response = new Response();
        FileUploadUtil.assertAllowed(photo, FileUploadUtil.IMAGE_PATTERN);
        try {
            String url = null;

            if(photo != null && !photo.isEmpty() ){
                final String publicId = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                final Map<String, String> params = new HashMap<>();
                params.put("public_id",FileUploadUtil.getFileName(file.getOriginalFilename()));
                final Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
                url = (String) uploadResult.get("secure_url");
                final String public_id = (String) uploadResult.get("publicId");

            }

            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomiseException("Room not found"));
            if(roomType != null) room.setRoomType(roomType);
            if(roomPrice != null) room.setRoomPrice(roomPrice);
            if(description != null) room.setDescription(description);
            if(url != null) room.setRoomPhotoURL(url);

            Room updatedRoom = roomRepository.save(room);

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setRoom(roomDTO);
            response.setMessage("Successfully updated room");
            response.setStatusCode(200);   
        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error on updating a room" + e.getMessage());

        }
        return response;
    }
    

}
