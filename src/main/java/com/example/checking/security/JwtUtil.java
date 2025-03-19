package com.example.checking.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {
    private final String SECRET = "mySecretKeymySecretKeymySecretKeymySecretKey"; 
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; 

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey()) 
                .compact();
    }
}
