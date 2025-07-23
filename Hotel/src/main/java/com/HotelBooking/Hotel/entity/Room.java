package com.HotelBooking.Hotel.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;

import lombok.Data;

@Data
@Table(name = "rooms")
@Entity

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;

    private BigDecimal roomPrice;

    private String roomPhotoURL;

    private String description;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Room [id=" + id + ", roomType=" + roomType + ", roomPrice=" + roomPrice + ", roomPhotoURL="
                + roomPhotoURL + ", description=" + description + "]";
    }

   
    







}
