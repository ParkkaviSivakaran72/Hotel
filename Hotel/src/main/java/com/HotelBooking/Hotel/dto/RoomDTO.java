package com.HotelBooking.Hotel.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoURL;
    private String description;
    private List<BookingDTO> bookings= new ArrayList<>();
}
