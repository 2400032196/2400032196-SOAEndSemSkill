package com.example.jwtvalidation.controller;

import com.example.jwtvalidation.dto.JwtValidationRequest;
import com.example.jwtvalidation.dto.JwtValidationResponse;
import com.example.jwtvalidation.service.JwtValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/jwt")
public class JwtValidationController {

    private final JwtValidator jwtValidator;

    public JwtValidationController(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @PostMapping("/validate")
    public ResponseEntity<JwtValidationResponse> validateToken(
            @RequestBody JwtValidationRequest request) {

        if (request == null || request.getToken() == null
                || request.getToken().isBlank()) {
            return ResponseEntity.badRequest().body(
                    new JwtValidationResponse(false, "Token is required", null,
                            Collections.emptyList()));
        }

        try {
            Claims claims = jwtValidator.validateAndExtractClaims(request.getToken());
            String username = claims.get("username", String.class);
            List<String> roles = jwtValidator.extractRoles(claims);

            return ResponseEntity.ok(
                    new JwtValidationResponse(true, "Token Valid", username, roles));
        } catch (JwtException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new JwtValidationResponse(false, "Invalid Token", null,
                            Collections.emptyList()));
        }
    }
}
