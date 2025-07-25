package com.HotelBooking.Hotel.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.HotelBooking.Hotel.dto.BookingDTO;
import com.HotelBooking.Hotel.dto.RoomDTO;
import com.HotelBooking.Hotel.dto.UserDTO;
import com.HotelBooking.Hotel.entity.Booking;
import com.HotelBooking.Hotel.entity.Room;
import com.HotelBooking.Hotel.entity.User;
import com.amazonaws.services.s3.model.S3DataSource;
import com.fasterxml.jackson.databind.util.RootNameLookup;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i < length; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoURL(room.getRoomPhotoURL());
        roomDTO.setDescription(room.getDescription());
        
        return roomDTO;
    }
    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
        bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        bookingDTO.setTotalNumberOfGuests(booking.getTotalNumberOfGuests());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

        return bookingDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTOBookings(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoURL(room.getRoomPhotoURL());
        roomDTO.setDescription(room.getDescription());

        if(room.getBookings() != null){
            roomDTO.setBookings(room.getBookings().stream()
            .map(Utils :: mapBookingEntityToBookingDTO)
            .collect(Collectors.toList()));
        }
        
        return roomDTO;
    }

    public static BookingDTO mapBookingEntityBookingDTOBookedRooms(Booking booking, boolean mapUser){
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
        bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        bookingDTO.setTotalNumberOfGuests(booking.getTotalNumberOfGuests());
        bookingDTO.setConfirmationCode(booking.getConfirmationCode());

        if(mapUser){
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }
        if(booking.getRoom() != null){
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoURL(booking.getRoom().getRoomPhotoURL());
            roomDTO.setDescription(booking.getRoom().getDescription());

        }
        return bookingDTO;
    }

    public static UserDTO mapUserEntityToUserDTOUserBookingAndRooms(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if(!user.getBookings().isEmpty()){
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityBookingDTOBookedRooms(booking, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    public static List<UserDTO> mapUserEntityToUserListDTO(List<User> userList){
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }
    public static List<RoomDTO> maproomEntityToRoomListDTO(List<Room> roomList){
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> mapBookingEntityToBookingListDTO(List<Booking> bookingList){
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }

}
