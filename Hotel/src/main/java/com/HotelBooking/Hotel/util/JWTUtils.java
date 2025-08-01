package com.HotelBooking.Hotel.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTUtils {
    private static final long EXPIRATION_TIME = (1000 * 600 * 24 * 7);
    private final SecretKey Key;

    public JWTUtils() {
        String secretString = "dGhpcy1pcw1hLXZhbGlkLXNlY3JldC1zdHJpbmctZm9yLUpXVA=="; // base64 of "this-is-a-valid-secret-string-for-JWT"
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes,"HmacSHA256");
    }
        
    public String generateToken(UserDetails userdetails){
        return Jwts.builder().subject(userdetails.getUsername())
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                            .signWith(Key)
                            .compact();
    }
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }
    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
