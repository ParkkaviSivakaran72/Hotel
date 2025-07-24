package com.HotelBooking.Hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Import the required packages

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);

		Dotenv dotenv = Dotenv.load();
		Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
		System.out.println(cloudinary.config.cloudName);
	}

}
