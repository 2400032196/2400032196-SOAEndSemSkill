package com.example.jwtvalidation.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    // Base64-encoded 256-bit demo key. Replace with an environment variable in real deployments.
    private static final String SECRET =
            "bXktc3VwZXItc2VjdXJlLWRlbW8ta2V5LWZvci1qd3QtdmFsaWRhdGlvbi0yNTYtYml0cw==";

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
}
