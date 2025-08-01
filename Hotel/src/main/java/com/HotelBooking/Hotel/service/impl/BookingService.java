package com.HotelBooking.Hotel.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.BookingDTO;
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

public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private IBookingService roomServices;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Response SaveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try {
            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
                throw new IllegalArgumentException("Check in date must come before check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomiseException("Room not found"));
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomiseException("User not found"));

            List<Booking> existingBookings = room.getBookings();
            if (!roomIsAvailable(bookingRequest, existingBookings)) {
                throw new CustomiseException("Room not available for the selected date range");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setConfirmationCode(bookingConfirmationCode);

            response.setMessage("Successful");
            response.setStatusCode(200);

        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        return response;

    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new CustomiseException("booking not found"));
            bookingRepository.deleteById(bookingId);

            response.setMessage("Successful");
            response.setStatusCode(200);

        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            Booking booking = bookingRepository.findByConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new CustomiseException("Booking not code not found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityBookingDTOBookedRooms(booking, true);
            response.setMessage("Successful");
            response.setStatusCode(200);
            response.setBooking(bookingDTO);

        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingListDTO = Utils.mapBookingEntityToBookingListDTO(bookingList);
            response.setMessage("Successful on getting all bookings");
            response.setStatusCode(200);
            response.setBookingList(bookingListDTO);

        } catch (CustomiseException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream().noneMatch(
                existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()) ||
                        bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()) ||
                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate()))
                        ||
                        (existingBooking.getCheckInDate().isBefore(bookingRequest.getCheckOutDate()) &&
                                existingBooking.getCheckOutDate().isAfter(bookingRequest.getCheckInDate()))
                        ||
                        (bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate()) &&
                                bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())));
    }

    @Override
    public Response addNewRoom(MultipartFile photo, MultipartFile file, String roomType, BigDecimal roomPrice,
            String description) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNewRoom'");
    }

}
