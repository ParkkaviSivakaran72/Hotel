package com.HotelBooking.Hotel.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        final Map<String,String> config = new HashMap<>();
        config.put("cloud_name","dmdej2vts");
        config.put("api_key","263899287473629");
        config.put("api_secret","P1nJHiQa_S_44QuLmSnZvcOkXfM");

        return new Cloudinary(config);
    }
}
