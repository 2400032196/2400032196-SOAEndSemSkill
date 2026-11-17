package com.example.jwtvalidation.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class JwtValidator {

    private final SecretKey secretKey;

    public JwtValidator(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public Claims validateAndExtractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public List<String> extractRoles(Claims claims) {
        Object rolesClaim = claims.get("roles");

        if (rolesClaim instanceof List<?> values) {
            List<String> roles = new ArrayList<>();
            for (Object value : values) {
                roles.add(String.valueOf(value));
            }
            return roles;
        }

        if (rolesClaim instanceof String role) {
            return List.of(role);
        }

        return Collections.emptyList();
    }
}
