package com.HotelBooking.Hotel.service.interfac;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.entity.Booking;

public interface IBookingService {
    Response SaveBooking(Long roomId, Long userId,  Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

    public Response addNewRoom(MultipartFile photo, MultipartFile file, String roomType, BigDecimal roomPrice, String description);
}

