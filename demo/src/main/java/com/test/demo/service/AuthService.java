package com.test.demo.service;
import java.util.Base64;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

    public String authenticate(String username, String password) {
        
        if ("vinathi".equals(username) && "vinathi17".equals(password)) {
           
            String token = generateJwtToken(username);
            return token;
        } else {
            
            return null;
        }
    }

    private String generateJwtToken(String username) {
        // Generate a secure key for HS256
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, base64Key)
                .compact();
    }
}
