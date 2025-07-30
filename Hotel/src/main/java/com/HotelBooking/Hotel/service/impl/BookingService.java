package com.HotelBooking.Hotel.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.entity.Booking;
import com.HotelBooking.Hotel.entity.Room;
import com.HotelBooking.Hotel.entity.User;
import com.HotelBooking.Hotel.exception.CustomiseException;
import com.HotelBooking.Hotel.repo.BookingRepository;
import com.HotelBooking.Hotel.repo.RoomRepository;
import com.HotelBooking.Hotel.repo.UserRepository;
import com.HotelBooking.Hotel.service.interfac.IBookingService;
import com.HotelBooking.Hotel.util.Utils;
import com.amazonaws.services.s3.model.S3DataSource;

public class BookingService implements IBookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IBookingService roomServices;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired UserRepository userRepository;

    @Override
    public Response SaveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                throw new IllegalArgumentException("Check in date must come before check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomiseException("Room not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomiseException("User not found"));

            List<Booking> existingBookings = room.getBookings();
            if(!roomIsAvailable(bookingRequest, existingBookings)){
                throw new CustomiseException("Room not available for the selected date range");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setConfirmationCode(bookingConfirmationCode);

            response.setMessage("Successful");
            response.setStatusCode(200);

        } catch(CustomiseException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;

    }

    @Override
    public Response cancelBooking() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getAllBookings() {
        // TODO Auto-generated method stub
        return null;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
    return existingBookings.stream().noneMatch(existingBooking ->
        bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()) ||
        bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()) ||
        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
         bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())) ||
        (existingBooking.getCheckInDate().isBefore(bookingRequest.getCheckOutDate()) &&
         existingBooking.getCheckOutDate().isAfter(bookingRequest.getCheckInDate())) ||
        (bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate()) &&
         bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
    );
}

}
