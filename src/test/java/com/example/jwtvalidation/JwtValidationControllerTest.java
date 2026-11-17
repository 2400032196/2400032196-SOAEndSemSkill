package com.example.jwtvalidation;

import com.example.jwtvalidation.dto.JwtValidationRequest;
import com.example.jwtvalidation.service.JwtValidator;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JwtValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecretKey jwtSecretKey;

    @Test
    void validTokenReturnsClaims() throws Exception {
        String token = Jwts.builder()
                .claim("username", "madhuri")
                .claim("roles", List.of("USER", "ADMIN"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(jwtSecretKey)
                .compact();

        String json = "{\"token\":\"" + token + "\"}";

        mockMvc.perform(post("/api/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.message").value("Token Valid"))
                .andExpect(jsonPath("$.username").value("madhuri"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }

    @Test
    void invalidTokenReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/jwt/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"not-a-valid-jwt\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.valid").value(false))
                .andExpect(jsonPath("$.message").value("Invalid Token"));
    }
}
