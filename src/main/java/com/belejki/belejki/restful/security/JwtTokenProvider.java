package com.belejki.belejki.restful.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
    private final SecretKey jwtSecret;// Change to a secure secret

    public JwtTokenProvider(SecretKey jwtSecret) {
        this.jwtSecret = jwtSecret;
    }


    // Method to generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours expiration
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Method to extract username from the token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
