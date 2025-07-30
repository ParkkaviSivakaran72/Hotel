package com.HotelBooking.Hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.service.interfac.IRoomInterface;

public class RoomService implements IRoomInterface {

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal RoomPrice, String description) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAllAvailableRooms() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getAllRoomTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAllRooms() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getRoomById(Long roomId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice,
            MultipartFile photo) {
        // TODO Auto-generated method stub
        return null;
    }
    

}
