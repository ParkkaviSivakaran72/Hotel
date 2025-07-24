package com.HotelBooking.Hotel.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check in Date is required")
    private LocalDate checkInDate;

    @Future(message="Check out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message="Number of Adults should not be less than one")
    private int numberOfAdults;

    @Min(value = 0, message = "Number of children should not be less than zero")
    private int numberOfChildren;
    private int totalNumberOfGuests;
    private String confirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void calculateTotalNumberofGuests(){
        this.totalNumberOfGuests = this.numberOfAdults + this.numberOfChildren;
    }

    public void setNumberOfAdults(int numberOfAdults){
        this.numberOfAdults = numberOfAdults;
        calculateTotalNumberofGuests();
    }
    public void setNumberOfChildren(int numberOfChildren){
        this.numberOfChildren = numberOfChildren;
        calculateTotalNumberofGuests();
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate
                + ", numberOfAdults=" + numberOfAdults + ", numberOfChildren=" + numberOfChildren
                + ", totalNumberOfGuests=" + totalNumberOfGuests + ", confirmationCode=" + confirmationCode + "]";
    }

    
    
}
