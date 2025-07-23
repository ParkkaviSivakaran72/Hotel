package com.HotelBooking.Hotel.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private Long id;
    private LocalDate CheckInDate;
    private LocalDate CheckOutDate;
    private int numberOfAdults;
    private int numberOfChildren;
    private int totalNumberOfGuests;
    private String cofirmationCode;
    private UserDTO user;
    private RoomDTO room;
    
}
