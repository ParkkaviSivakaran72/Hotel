package com.HotelBooking.Hotel.service.interfac;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.Response;

public interface IRoomInterface {
    Response addNewRoom(MultipartFile photo,MultipartFile file, String roomType,BigDecimal RoomPrice,String description);

    List<String> getAllRoomTypes();

    Response getAllRooms();

    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo, MultipartFile file);

    Response getRoomById(Long roomId);

    Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType );

    Response getAllAvailableRooms();
}
