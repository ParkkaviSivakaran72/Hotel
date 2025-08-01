package com.HotelBooking.Hotel.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.HotelBooking.Hotel.dto.Response;
import com.HotelBooking.Hotel.service.interfac.IRoomInterface;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private IRoomInterface roomService;
    
    // @Autowired
    // private IBookingService bookingService;

    @PostMapping("/add")
    public ResponseEntity<Response> addNewRoom(
        @RequestParam(value = "photo", required=false) MultipartFile photo,
        @RequestParam(value = "file", required=false) MultipartFile file,
        @RequestParam(value = "roomType", required=false) String roomType,
        @RequestParam(value = "roomPrice", required=false) BigDecimal roomPrice,
        @RequestParam(value = "description", required=false) String description

    ){
        if(photo == null || photo.isEmpty() || roomType == null || roomType.isBlank() || roomPrice == null){
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please enter all fields");
            return ResponseEntity.status(response.getStatusCode()).body(response);
            
        }
        Response response = roomService.addNewRoom(photo,file, roomType,roomPrice, description);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms(){
        Response response = roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable("roomId") Long roomId){
        Response response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
        
    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAvailableRooms(){
        Response response = roomService.getAllAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getAvailableRoomsByDateAndTypes(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
        @RequestParam(required = false) String roomType
    ){
        if(checkInDate == null || checkOutDate == null || roomType.isBlank()){
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("All fields are required!");
        }
        Response response = roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(
        @PathVariable Long roomId,
        @RequestParam(value = "photo", required=false) MultipartFile photo,
        @RequestParam(value = "file", required=false) MultipartFile file,
        @RequestParam(value = "roomType", required=false) String roomType,
        @RequestParam(value = "roomPrice", required=false) BigDecimal roomPrice,
        @RequestParam(value = "description", required=false) String description

    ){
        
        Response response = roomService.updateRoom(roomId,description, roomType,roomPrice, photo, file);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId){
        Response response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



}
