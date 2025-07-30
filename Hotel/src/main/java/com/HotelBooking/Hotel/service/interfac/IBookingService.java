package com.HotelBooking.Hotel.service.interfac;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.entity.Booking;

public interface IBookingService {
    Response SaveBooking(Long roomId, Long userId,  Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking();
}

