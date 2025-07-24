package com.HotelBooking.Hotel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelBooking.Hotel.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByConfirmationCode(String confirmationCode);

}
