package com.enterprise.inbox.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class JwtServiceTest {

    @Test
    void generatesAndValidatesToken() {
        JwtProperties properties = new JwtProperties();
        properties.setSecret("test-secret-key-that-is-at-least-32-bytes-long");
        properties.setIssuer("enterprise-inbox-backend");
        properties.setExpirationSeconds(1200);
        JwtService jwtService = new JwtService(properties);

        String token = jwtService.generateToken("admin", List.of("ROLE_ADMIN"));

        assertThat(jwtService.isTokenValid(token)).isTrue();
        assertThat(jwtService.extractUsername(token)).isEqualTo("admin");
        assertThat(jwtService.extractRoles(token)).contains("ROLE_ADMIN");
    }

    @Test
    void rejectsTamperedToken() {
        JwtProperties properties = new JwtProperties();
        properties.setSecret("test-secret-key-that-is-at-least-32-bytes-long");
        properties.setIssuer("enterprise-inbox-backend");
        properties.setExpirationSeconds(1200);
        JwtService jwtService = new JwtService(properties);

        String token = jwtService.generateToken("analyst", List.of("ROLE_ANALYST"));
        String tampered = token + "tamper";

        assertThat(jwtService.isTokenValid(tampered)).isFalse();
    }
}
